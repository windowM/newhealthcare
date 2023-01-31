package com.example.newhealthcare.service;

import com.example.newhealthcare.Header;
import com.example.newhealthcare.dto.DoctorPreResponseDTO;
import com.example.newhealthcare.dto.PatientDiagnosisResponseDTO;
import com.example.newhealthcare.dto.PatientPreResponseDTO;
import com.example.newhealthcare.dto.PatientResponseDTO;
import com.example.newhealthcare.model.entity.*;
import com.example.newhealthcare.model.network.request.DandPApiRequest;
import com.example.newhealthcare.model.network.request.doctor.DoctorDiagnosisApiRequest;
import com.example.newhealthcare.model.network.request.doctor.DoctorPreApiRequest;
import com.example.newhealthcare.model.network.response.doctor.DoctorDiagnosisApiResponse;
import com.example.newhealthcare.model.network.response.patient.PatientDiagnosisApiResponse;
import com.example.newhealthcare.repository.DiagnosisRepository;
import com.example.newhealthcare.repository.DoctorRepository;
import com.example.newhealthcare.repository.PatientRepository;
import com.example.newhealthcare.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiagnosisService {
    @Autowired
    private DiagnosisRepository diagnosisRepository;
    @Autowired
    private PrescriptionRepository prescriptionRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepository patientRepository;

    //의사 : 진단 화면 불러오기
    public Header<List<PatientResponseDTO>> read(String id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        if (doctor.isPresent()) {
            List<PatientResponseDTO> responseDTOS = new ArrayList<PatientResponseDTO>();
            doctor.get().getDandpList().stream().forEach(dandP -> {
                PatientResponseDTO patient = PatientResponseDTO.builder()
                        .patientId(dandP.getPatientId().getPatientId())
                        .name(dandP.getPatientId().getName())
                        .gender(dandP.getPatientId().getGender())
                        .born(dandP.getPatientId().getBorn())
                        .phone(dandP.getPatientId().getPhone())
                        .build();
                responseDTOS.add(patient);
            });
            return Header.OK(responseDTOS);
        } else {
            return Header.ERROR("의사 없음");
        }
    }

    //의사 : 진단 화면 불러오기
    public Header<List<DoctorDiagnosisApiResponse>> detail(Header<DandPApiRequest> request) {
        DandPApiRequest request1 = request.getData();
        Optional<Patient> patient = patientRepository.findById(request1.getPatientId());
        Optional<Doctor> doctor = doctorRepository.findById(request1.getDoctorId());

        if (patient.isPresent() && doctor.isPresent()) {
            List<Diagnosis> diagnosisList = diagnosisRepository.findByDoctorIdAndSelPatientId(doctor.get(), patient.get());

            if (!diagnosisList.isEmpty()) {
                //return 해줄 리스트 생성
                List<DoctorDiagnosisApiResponse> responseList = new ArrayList<DoctorDiagnosisApiResponse>();

                for (int i = 0; i < diagnosisList.size(); i++) {
                    //진단에 대한 처방 ( 진단 1:N 처방 )
                    List<Prescription> prescriptionList = prescriptionRepository.findByDiaNum(diagnosisList.get(i));
                    List<DoctorPreResponseDTO> preList = new ArrayList<DoctorPreResponseDTO>();

                    if (!prescriptionList.isEmpty()) {
                        for (int j = 0; j < prescriptionList.size(); j++) {
                            DoctorPreResponseDTO preResponseDTO = DoctorPreResponseDTO.builder()
                                    .diaNum(prescriptionList.get(j).getDiaNum().getDiaNum())
                                    .preNum(prescriptionList.get(j).getPreNum())
                                    .contents(prescriptionList.get(j).getContents())
                                    .preName(prescriptionList.get(j).getPreName())
                                    .oneDay(prescriptionList.get(j).getOneDay())
                                    .total(prescriptionList.get(j).getTotal())
                                    .preDate(prescriptionList.get(j).getPreDate())
                                    .build();
                            preList.add(preResponseDTO);
                        }
                    }

                    DoctorDiagnosisApiResponse response = DoctorDiagnosisApiResponse.builder()
                            .diaNum(diagnosisList.get(i).getDiaNum())
                            .doctorId(request1.getDoctorId())
                            .selectPatientId(request1.getPatientId())
                            .contents(diagnosisList.get(i).getContents())
                            .disease(diagnosisList.get(i).getDisease())
                            .diaDate(diagnosisList.get(i).getDiaDate())
                            .prescriptionList(preList)
                            .build();
                    responseList.add(response);

                }
                return Header.OK(responseList);
            } else {
                return Header.ERROR("진단 정보가 없다.");
            }
        } else {
            return Header.ERROR("환자 정보가 없다.");
        }
    }


    //의사 : 진단 create, return diaNum 지금은 연결되지 않은 환자에 대한 예외처리 안함
    public Header<DoctorDiagnosisApiResponse> diaCreate(Header<DoctorDiagnosisApiRequest> request) {

        DoctorDiagnosisApiRequest request1 = request.getData();

        Optional<Doctor> diaDoctor = doctorRepository.findById(request1.getDoctorId());
        Optional<Patient> selPatient = patientRepository.findById(request1.getSelPatientId());


        if (diaDoctor.isPresent() && selPatient.isPresent()) {
            Diagnosis diagnosis1 = Diagnosis.builder()
                    .doctorId(diaDoctor.get())
                    .selPatientId(selPatient.get())
                    .disease(request1.getDisease())
                    .contents(request1.getContents())
                    .diaDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")))
                    .build();

            diagnosisRepository.save(diagnosis1);

            System.out.println(diagnosis1.getDiaNum());

            DoctorDiagnosisApiResponse response = DoctorDiagnosisApiResponse.builder()
                    .diaNum(diagnosis1.getDiaNum())
                    .build();

            return Header.OK(response);
        } else {
            return Header.ERROR("의사/환자 정보가 없다.");
        }

    }

    //의사 : 처방 create
    public Header preCreate(Long diaNum, Header<DoctorPreApiRequest> request) {
        Optional<Diagnosis> diagnosis = diagnosisRepository.findById(diaNum);
        if (diagnosis.isPresent()) {
            DoctorPreApiRequest request1 = request.getData();
            Prescription prescription = Prescription.builder()
                    .diaNum(diagnosis.get())
                    .contents(request1.getContents())
                    .preName(request1.getPreName())
                    .oneDay(request1.getOneDay())
                    .total(request1.getTotal())
                    .preDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")))
                    .build();

            prescriptionRepository.save(prescription);

            return Header.OK();
        } else {
            return Header.ERROR("존재하지 않는 진단번호");
        }
    }

    //환자 : 진료 내역 화면
    public Header<PatientDiagnosisApiResponse> showList(String id) {
        Optional<Patient> patient = patientRepository.findById(id);
        List<PatientDiagnosisResponseDTO> diagnosisResponseDTOS=new ArrayList<PatientDiagnosisResponseDTO>();

        if (patient.isPresent()){
           List<Diagnosis> diagnosisList=patient.get().getDiagnosisList();

           for( int i =0;i< diagnosisList.size();i++){
               List<Prescription> prescriptionList=diagnosisList.get(i).getPrescriptionList();
               List<PatientPreResponseDTO> preResponseDTOS=new ArrayList<PatientPreResponseDTO>();

               for(int j=0;j<prescriptionList.size();j++){
                   PatientPreResponseDTO preResponseDTO=PatientPreResponseDTO.builder()
                           .diaNum(prescriptionList.get(j).getDiaNum().getDiaNum())
                           .preNum(prescriptionList.get(j).getPreNum())
                           .preName(prescriptionList.get(j).getPreName())
                           .preContents(prescriptionList.get(j).getContents())
                           .oneDay(prescriptionList.get(j).getOneDay())
                           .total(prescriptionList.get(j).getTotal())
                           .preDate(prescriptionList.get(j).getPreDate())
                           .build();
                   preResponseDTOS.add(preResponseDTO);
               }
               PatientDiagnosisResponseDTO diaResponseDTO=PatientDiagnosisResponseDTO.builder()
                       .diaNum(diagnosisList.get(i).getDiaNum())
                       .diaDoctorId(diagnosisList.get(i).getDoctorId().getDoctorId())
                       .diaContents(diagnosisList.get(i).getContents())
                       .disease(diagnosisList.get(i).getDisease())
                       .prescriptionList(preResponseDTOS)
                       .diaDate(diagnosisList.get(i).getDiaDate())
                       .build();
               diagnosisResponseDTOS.add(diaResponseDTO);
           }
        }

        PatientDiagnosisApiResponse responseList=PatientDiagnosisApiResponse.builder()
                .patientId(id)
                .diagnosisList(diagnosisResponseDTOS)
                .build();
        return Header.OK(responseList);

    }
}
