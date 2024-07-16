package pl.bank.gr.service.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class ExternalExchangeRateDto {
	private String code;
	private List<ExternalExchangeRateRatesDto> rates;

}
