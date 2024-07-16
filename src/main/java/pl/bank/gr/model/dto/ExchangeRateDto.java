package pl.bank.gr.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExchangeRateDto {
	private double rateValue;
	private String currencyCode;

}
