package io.github.ovso.onea.data;

import io.github.ovso.onea.ui.utils.MarketType;
import io.github.ovso.onea.ui.utils.SimOperator;
import lombok.Builder;
import lombok.Data;

@Data @Builder public class HeaderInfo {
  private SimOperator.Type operatorType;
  private MarketType marketType;
  private String email;
}