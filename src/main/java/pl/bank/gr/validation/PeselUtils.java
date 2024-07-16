package pl.bank.gr.validation;

public class PeselUtils {

	public static Long getChecksum(String pesel) {

		long num = Long.parseLong(pesel);
		long tab[] = new long[11];
		long weights[] = { 1, 3, 7, 9, 1, 3, 7, 9, 1, 3, 1 };

		for (int i = 0; i < 11; i++) {
			tab[i] = getDigitFromPos(num, i);
		}

		long sum = 0;
		for (int i = 0; i < 10; i++) {
			sum += weights[i] * tab[i];
		}
		sum = 10 - (sum % 10);
		return sum % 10;
	}

	public static Long getBirthYear(String pesel) {
		Long year;
		Long month;

		if (!isPeselValid(pesel))
			return null;

		Long num = Long.parseLong(pesel);

		year = 10 * getDigitFromPos(num, 0);
		year += getDigitFromPos(num, 1);
		month = 10 * getDigitFromPos(num, 2);
		month += getDigitFromPos(num, 3);
		
		if (month > 80 && month < 93) {
			year += 1800;
		} else if (month > 0 && month < 13) {
			year += 1900;
		} else if (month > 20 && month < 33) {
			year += 2000;
		} else if (month > 40 && month < 53) {
			year += 2100;
		} else if (month > 60 && month < 73) {
			year += 2200;
		}
		return year;
	}

	public static Long getBirthMonth(String pesel) {
		Long month;

		if (!isPeselValid(pesel))
			return null;

		Long num = Long.parseLong(pesel);

		month = 10 * getDigitFromPos(num, 2);
		month += getDigitFromPos(num, 3);
		if (month > 80 && month < 93) {
			month -= 80;
		} else if (month > 20 && month < 33) {
			month -= 20;
		} else if (month > 40 && month < 53) {
			month -= 40;
		} else if (month > 60 && month < 73) {
			month -= 60;
		}
		return month;
	}

	public static Long getBirthDay(String pesel) {
		Long day;

		if (!isPeselValid(pesel))
			return null;

		Long num = Long.parseLong(pesel);

		day = 10 * getDigitFromPos(num, 4);
		day += getDigitFromPos(num, 5);

		return day;
	}

	public static String getBirthDayString(String pesel) {

		if (!isPeselValid(pesel))
			return null;

		return getBirthYear(pesel) + "-" 
				+ (getBirthMonth(pesel).toString().length() == 1 ? "0" : "")
				+ getBirthMonth(pesel) + "-" 
				+ (getBirthDay(pesel).toString().length() == 1 ? "0" : "")
				+ getBirthDay(pesel);

	}

	public static boolean isPeselValid(String pesel) {

		if (pesel == null)
			return false;

		if (pesel.length() != 11)
			return false;

		if (!getDigitFromPos(Long.parseLong(pesel), 10).equals(
				getChecksum(pesel)))
			return false;

		return true;
	}
	
	public static Long getDigitFromPos(Long number, Integer pos) {
		Long result = Long.parseLong(number.toString().substring(pos, pos + 1));
		return result;
	}

}