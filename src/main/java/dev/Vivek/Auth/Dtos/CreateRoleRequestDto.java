package dev.Vivek.Auth.Dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRoleRequestDto {
    private String name;


    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
}