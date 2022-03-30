package com.adrian.cheetahdigital.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private String firstName;
    private String secondName;
    private List<String> tags;

    @Override
    public int hashCode() {
        return Objects.hash(firstName, secondName);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Result result = (Result) obj;
        return (Objects.equals(firstName, result.firstName) && Objects.equals(secondName, result.secondName)) || (Objects.equals(secondName, result.firstName) && Objects.equals(firstName, result.secondName));
    }
}
