package com.example.newhealthcare.service;

import com.example.newhealthcare.Header;
import com.example.newhealthcare.dto.DoctorResponseDTO;
import com.example.newhealthcare.itf.CrudInterface;
import com.example.newhealthcare.model.entity.DandP;
import com.example.newhealthcare.model.entity.Doctor;
import com.example.newhealthcare.model.entity.Patient;
import com.example.newhealthcare.model.network.request.patient.PatientApiRequest;
import com.example.newhealthcare.model.network.response.patient.PatientApiResponse;
import com.example.newhealthcare.repository.DandPRepository;
import com.example.newhealthcare.repository.DoctorRepository;
import com.example.newhealthcare.repository.PatientRepository;
import com.example.newhealthcare.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService  implements CrudInterface<PatientApiRequest, PatientApiResponse> {
    @Autowired
    private final PatientRepository patientRepository;
    @Autowired
    private final DoctorRepository doctorRepository;
    @Autowired
    private final DandPRepository dandPRepository;
    @Autowired
    private final SensorRepository sensorRepository;

    //환자 로그인
    public Header login(Header<PatientApiRequest> request){
        PatientApiRequest patientApiRequest=request.getData();
        Optional<Patient> patient=patientRepository.findById(patientApiRequest.getPatientId());
        return patient.map(patient1->{
            if(patient1.getPassword().equals(patientApiRequest.getPassword())){
                return Header.OK();
            }else{
                return Header.ERROR("틀린 비밀번호 입니다.");
            }
        }).orElseGet(()->Header.ERROR("없는 회원 정보입니다."));
    }

    //환자 회원가입
    @Override
    public Header<PatientApiResponse> create(Header<PatientApiRequest> request) {
        PatientApiRequest patientApiRequest=request.getData();
        Optional<Patient> patient=patientRepository.findById(patientApiRequest.getPatientId());
        if(!patient.isPresent()){
            Patient Patient1=Patient.builder().
                        patientId(patientApiRequest.getPatientId()).
                        password(patientApiRequest.getPassword()).
                        gender(patientApiRequest.getGender()).
                        born(patientApiRequest.getBorn()).
                        email(patientApiRequest.getEmail()).
                        name(patientApiRequest.getName()).
                        phone(patientApiRequest.getPhone()).
                        build();

            Patient newPatient=patientRepository.save(Patient1);
            return response(newPatient);
        }else{
            return Header.ERROR("존재하는 회원");
        }
    }

    //환자 홈 화면 데이터 제공
    @Override
    public Header<PatientApiResponse> read(String id) {
        Optional<Patient> patient = patientRepository.findById(id);
        return patient
                .map(patient1 -> {
                    List<DoctorResponseDTO> doctorResponseDTOS = new ArrayList<DoctorResponseDTO>();
                    patient1.getDandpList().stream().forEach(dandP -> {
                        DoctorResponseDTO doctorResponseDTO = DoctorResponseDTO.builder()
                                .name(dandP.getDoctorId().getName())
                                .gender(dandP.getDoctorId().getGender())
                                .phone(dandP.getDoctorId().getPhone())
                                .major(dandP.getDoctorId().getMajor())
                                .email(dandP.getDoctorId().getEmail())
                                .build();
                        doctorResponseDTOS.add(doctorResponseDTO);
                    });

                    PatientApiResponse patientApiResponse = PatientApiResponse.builder()
                            .patientId(patient1.getPatientId())
                            .email(patient1.getEmail())
                            .name(patient1.getName())
                            .gender(patient1.getGender())
                            .born(patient1.getBorn())
                            .doctorId(doctorResponseDTOS)
                            .build();
                    return Header.OK(patientApiResponse);

                }).orElseGet(() -> Header.ERROR("정보 없음"));
    }

    //환자 정보 수정
    @Override
    public Header<PatientApiResponse> update(Header<PatientApiRequest> request) {
        PatientApiRequest patientApiRequest=request.getData();
        Optional<Patient> patient=patientRepository.findById(patientApiRequest.getPatientId());
        return patient.map(patient1 -> {
            patient1.setPassword(patientApiRequest.getPassword())
                    .setBorn(patientApiRequest.getBorn())
                    .setEmail(patientApiRequest.getEmail())
                    .setName(patientApiRequest.getName())
                    .setPhone(patientApiRequest.getPhone());
            return patient1;
        })
        .map(patient1 -> patientRepository.save(patient1))
        .map(updatePatient->response(updatePatient))
        .orElseGet(()->Header.ERROR("환자정보 없음"));
    }

    //환자 코드번호 입력
    public Header inputCode(String id,Header<PatientApiRequest> request){
        PatientApiRequest patientApiRequest= request.getData();
        Optional<Patient> patient=patientRepository.findById(id);
        return patient
                .map(patient1 ->{
                    Optional<Doctor> doctor1=doctorRepository.findByCode(patientApiRequest.getCode());
                    Optional<DandP> dandp=dandPRepository.findByCode(patientApiRequest.getCode());
                    //기존 연결된 코드번호가 요청 되었을때 예외 처리
                    if(dandp.isPresent()){
                        return Header.ERROR("이미 매칭된 코드 번호입니다.");
                    }else if(!doctor1.isPresent()){
                        return Header.ERROR("코드와 연관된 의사를 찾을 수 없습니다.");
                    }
                    else {
                        Doctor conDoc=doctor1.get();
                        DandP dandP1 = DandP.builder()
                                .code(patientApiRequest.getCode())
                                .patientId(patient1)
                                .doctorId(conDoc)
                                .build();
                        dandPRepository.save(dandP1);

                        return Header.OK();
                    }
        }).orElseGet(()->Header.ERROR("환자 정보가 없음"));
    }

    //환자 회원 삭제
    @Override
    public Header delete(String id) {
        Optional<Patient> patient=patientRepository.findById(id);
        return patient.map(patient1 -> {
            patientRepository.delete(patient1);
            return Header.OK();
        })
        .orElseGet(()->Header.ERROR("환자 정보 없음"));
    }

    public Header<PatientApiResponse> response(Patient patient){
        PatientApiResponse patientApiResponse= PatientApiResponse.builder().
                patientId(patient.getPatientId()).
                password(patient.getPassword()).
                gender(patient.getGender()).
                born(patient.getBorn()).
                email(patient.getEmail()).
                name(patient.getName()).
                phone(patient.getPhone()).
                //doctorId(patient.getDandpList()).
                build();
        return Header.OK(patientApiResponse);
    }

}
