package com.example.backend.bootstrap;

import com.example.backend.entities.EmergencyType;
import com.example.backend.repository.EmergencyTypeRepository;
import com.example.backend.entities.EmergencyTypeEnum;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class EmergencyTypeSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final EmergencyTypeRepository emergencyTypeRepository;

    public EmergencyTypeSeeder( EmergencyTypeRepository emergencyTypeRepository){
        this.emergencyTypeRepository = emergencyTypeRepository;

    }

    @Override
    public void onApplicationEvent ( ContextRefreshedEvent contextRefreshedEvent) {this.loadEmergencyTypes();}

    private void loadEmergencyTypes(){

        EmergencyTypeEnum[] emergencyTypeNames = new EmergencyTypeEnum[] { EmergencyTypeEnum.STROKE,EmergencyTypeEnum.FAINT, EmergencyTypeEnum.HEART_ATTACK };

        Map<EmergencyTypeEnum, String> emergencyTypeDescriptionMap = Map.of(
                EmergencyTypeEnum.STROKE, "Paralysis",
                EmergencyTypeEnum.FAINT,"Not moving",
                EmergencyTypeEnum.HEART_ATTACK,"Heaving breathing"

        );
        Arrays.stream(emergencyTypeNames).forEach((emergencyTypeName) ->{
            Optional<EmergencyType> optionalEmergencyType = emergencyTypeRepository.findByName(emergencyTypeName);

            optionalEmergencyType.ifPresentOrElse(System.out:: println,() ->{
                EmergencyType emergencyTypeToCreate = new EmergencyType();

                emergencyTypeToCreate.setName(emergencyTypeName)
                        .setDescription(emergencyTypeDescriptionMap.get(emergencyTypeName));


                        emergencyTypeRepository.save(emergencyTypeToCreate);

            });
        });

    }


}

