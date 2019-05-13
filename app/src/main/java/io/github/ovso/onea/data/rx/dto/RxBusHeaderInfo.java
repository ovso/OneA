package io.github.ovso.onea.data.rx.dto;

import io.github.ovso.onea.utils.MarketType;
import io.github.ovso.onea.utils.SimOperator;
import lombok.Builder;
import lombok.Data;

@Data @Builder public class RxBusHeaderInfo {
  private SimOperator.Type operatorType;
  private MarketType marketType;
  private String email;
}