package dummyApp.visual.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.inject.Injector;
import com.premiumminds.billy.core.exceptions.BillyRuntimeException;
import com.premiumminds.billy.core.exceptions.InvalidTaxIdentificationNumberException;
import com.premiumminds.billy.portugal.services.entities.PTCustomer;

import dummyApp.app.AppManager;

public class CreateCustomerCLI {

	BufferedReader bufferReader = new BufferedReader(new InputStreamReader(
			System.in));
	AppManager manager;

	public CreateCustomerCLI(AppManager manager) {
		this.manager = manager;
	}

	public PTCustomer createCustomer() {
		String name;
		String taxNumber;
		String street;
		String number;
		String postalCode;
		String city;
		String telephone;

		try {
			System.out.println("Name:");
			name = bufferReader.readLine();
			if (name.equals("")) {
				name = "John Conner";
				System.out.println("Name: " + name);
			}
			
			System.out.println("Tax Registration Number:");
			taxNumber = bufferReader.readLine();
			if (taxNumber.equals("")) {
				taxNumber = "123456789";
				System.out.println("Tax Number: " + taxNumber);
			}
			
			System.out.println("Street name:");
			street = bufferReader.readLine();
			if (street.equals("")) {
				street = "Via Aurélia";
				System.out.println("Street: " + street);
			}
			
			System.out.println("Door number:");
			number = bufferReader.readLine();
			if (number.equals("")) {
				number = "654";
				System.out.println("Door Number: " + number);
			}
			
			System.out.println("Postal Code:");
			postalCode = bufferReader.readLine();
			if (postalCode.equals("")) {
				postalCode = "0001-001";
				System.out.println("Postal Code: " + postalCode);
			}
			
			System.out.println("City:");
			city = bufferReader.readLine();
			if (city.equals("")) {
				city = "Sena";
				System.out.println("City: " + city);
			}
			
			System.out.println("Telephone:");
			telephone = bufferReader.readLine();
			if (telephone.equals("")) {
				telephone = "987523146";
				System.out.println("Telephone: " + telephone);
			}

			PTCustomer customer = manager.createCustomer(name, taxNumber,
					street, number, postalCode, city, telephone);
			if (customer == null) {
				System.out.println("Something went wrong");
			}
			System.out.println("Customer: " + customer.getName() + " created.");
			return customer;
		}catch(InvalidTaxIdentificationNumberException e){
			System.out.println("[ERROR] An error ocurred with the tax identification number: " + e.toString());
		} 
		catch(BillyRuntimeException e){
			System.out.println("[ERROR] An error ocurred: " + e.toString());
		} 
		catch (IOException e) {
			System.out.println("[ERROR] An error ocurred at: " + e.toString());
		} catch (Exception e) {
			System.out.println("[ERROR] An error ocurred at: " + e.toString());
		}
		return null;
	}

}
