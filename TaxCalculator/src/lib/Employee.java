package lib;

import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;

public class Employee {

	private String employeeId;
	private String firstName;
	private String lastName;
	private String idNumber;
	private String address;
	
	private LocalDate joinDate;
	
	private boolean isForeigner;
	private Gender gender; 
	
	private int monthlySalary;
	private int otherMonthlyIncome;
	private int annualDeductible;

    private Spouse spouse; 
    private List<Child> children; 
	
	enum Gender {
		MALE, FEMALE
	}

    public Employee(String employeeId, String firstName, String lastName, String idNumber, String address,
                    LocalDate joinDate, boolean isForeigner, Gender gender) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.idNumber = idNumber;
        this.address = address;
        this.joinDate = joinDate;
        this.isForeigner = isForeigner;
        this.gender = gender;
        this.children = new ArrayList<>();
    }
	
	/**
	 * Fungsi untuk menentukan gaji bulanan pegawai berdasarkan grade kepegawaiannya (grade 1: 3.000.000 per bulan, grade 2: 5.000.000 per bulan, grade 3: 7.000.000 per bulan)
	 * Jika pegawai adalah warga negara asing gaji bulanan diperbesar sebanyak 50%
	 */
	
	public void setMonthlySalary(int grade) {
        // Duplicate Code - Pengulangan logic salary base
        int baseSalary = switch (grade) {
            case 1 -> 3000000;
            case 2 -> 5000000;
            case 3 -> 7000000;
            default -> 0;
        };
        monthlySalary = isForeigner ? (int) (baseSalary * 1.5) : baseSalary;
    }
	
	public void setAnnualDeductible(int deductible) {	
		this.annualDeductible = deductible;
	}
	
	public void setAdditionalIncome(int income) {	
		this.otherMonthlyIncome = income;
	}
	
	public void setSpouse(String spouseName, String spouseIdNumber) {
        this.spouse = new Spouse(spouseName, spouseIdNumber);
    }
	
	public void addChild(String childName, String childIdNumber) {
        this.children.add(new Child(childName, childIdNumber));
    }
	
	public int getAnnualIncomeTax() {
        int monthWorkingInYear = 12;
        LocalDate now = LocalDate.now();

        if (now.getYear() == joinDate.getYear()) {
            monthWorkingInYear = now.getMonthValue() - joinDate.getMonthValue();
        }

        boolean isMarried = spouse != null;
        int numberOfChildren = Math.min(children.size(), 3);

        return TaxFunction.calculateTax(
                monthlySalary,
                otherMonthlyIncome,
                monthWorkingInYear,
                annualDeductible,
                isMarried,
                numberOfChildren
        );
    }

	class Spouse {
		private String name;
		private String idNumber;
	
		public Spouse(String name, String idNumber) {
			this.name = name;
			this.idNumber = idNumber;
		}
	}
	
	class Child {
		private String name;
		private String idNumber;
	
		public Child(String name, String idNumber) {
			this.name = name;
			this.idNumber = idNumber;
		}
	}

}
