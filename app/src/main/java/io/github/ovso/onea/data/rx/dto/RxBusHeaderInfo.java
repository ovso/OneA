package io.github.ovso.onea.data.rx.dto;

import io.github.ovso.onea.ui.utils.MarketType;
import io.github.ovso.onea.ui.utils.SimOperator;
import lombok.Builder;
import lombok.Data;

@Data @Builder public class RxBusHeaderInfo {
  private SimOperator.Type operatorType;
  private MarketType marketType;
  private String email;
}