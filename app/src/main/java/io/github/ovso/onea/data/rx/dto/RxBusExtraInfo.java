package io.github.ovso.onea.data.rx.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder public class RxBusExtraInfo {
  private RxBusHeaderInfo headerInfo;
  private String extraServiceName;
}