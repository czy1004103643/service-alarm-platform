package com.newegg.ec.tool.service;


import java.util.Map;

public interface DealMessage {
  Map<String,Object> dealByUrl(String id);
}
