package com.example.Ceylone.Snippers.Back.End.utill;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommonResponse {
private boolean status = false;

private List<String> errorMessages = new ArrayList<>();

private List<Object> payload = null;

private String commonMessage;
private String token;

}
