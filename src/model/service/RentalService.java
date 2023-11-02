package model.service;

import java.time.Duration;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {
	
	private Double pricePerHour;
	private Double pricePerDay;
	
	private TaxService taxService; 

	public RentalService(Double pricePerHour, Double pricePerDay, TaxService taxService) {
		this.pricePerHour = pricePerHour;
		this.pricePerDay = pricePerDay;
		this.taxService = taxService;
	}
	
	public void processInvoice(CarRental carRental) {
		
		double minutes = Duration.between(carRental.getStart(), carRental.getFinish()).toMinutes(); //encontra a duração entre dois instantes em minutos
		double hours = minutes/60.0; //converter minutos em horas
		
		double basicPayment;
		if(hours <= 12) {
			basicPayment = pricePerHour * Math.ceil(hours); //arredondando as horas pra cima e multiplicando pelo preço por hora
		}
		else {
			basicPayment = pricePerDay * Math.ceil(hours/24.0);
		}
		
		double tax = taxService.tax(basicPayment);
		
		
		carRental.setInvoice(new Invoice(basicPayment, tax));
	}
	
}
