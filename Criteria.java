package com.example.dssMidtermV1;

import java.util.Scanner;

public class Criteria {
	String[] criteriaName;
	float[][] criteriaValue, criteriaNormalization;
	int nCriteria;
	float lamdaMax, CI, CR;
	boolean bCriteria=true;
	float[] criteriaValueTotal, criteriaNormalizationAverage, criteriaWeightedSum, criteriaWeightedSumAverage,crDivider = {0f,0f,0.58f,0.9f,1.12f,1.24f,1.32f,1.41f,1.45f,1.49f};
	
	public void printNormalization() {
		System.out.print("\tNormalization\t");
		for(int i=0;i<nCriteria;i++) {
			System.out.print("\t" + criteriaName[i]);
			if(criteriaName[i].length()<7) {
				System.out.print("\t");
			}
		}
		System.out.print("\t\tAverage");
		System.out.print("\t\tWeighted_Sum\t");
		System.out.print("\tWS/AVG");
		for(int j=0; j<this.nCriteria; j++) {
			System.out.print("\n\t " + criteriaName[j]);
			if(criteriaName[j].length()<7) {
				System.out.print("\t");
			}
			for(int k=0; k<this.nCriteria;k++) {
				System.out.printf("\t\t%.2f", criteriaNormalization[j][k]);
			}
			System.out.printf("\t\t%.2f", criteriaNormalizationAverage[j]);
			System.out.printf("\t\t%.2f", criteriaWeightedSum[j]);
			System.out.printf("\t\t\t%.2f", criteriaWeightedSumAverage[j]);
			if(j==0) {
				System.out.printf("\t\tLambda_Max: \t%.3f", lamdaMax);
			}else if(j==1) {
				System.out.printf("\t\tCI: \t\t%.3f", CI);
			}else if(j==2) {
				System.out.printf("\t\tCR: \t\t%.3f", CR);
			}
		}
	}
	public float[][] getCriteriaNormalization() {
		return criteriaNormalization;
	}

	public void setCriteriaNormalization() {
		float[][] ahpNormalization = new float [nCriteria][nCriteria];
		float[] ahpNormalizationAverage = new float[nCriteria], ahpWeightedSum = new float[nCriteria], ahpWeightedSumAverage = new float[nCriteria];
		float ahpLambdaMax = 0, ahpCI = 0, ahpCR = 0;
		for(int i=0;i<nCriteria;i++) {
			for(int j=0;j<nCriteria;j++) {
				ahpNormalization[i][j] = criteriaValue[i][j]/criteriaValueTotal[j];
			}
		}
		this.criteriaNormalization = ahpNormalization;
		for(int i=0; i<nCriteria;i++) {
			ahpNormalizationAverage[i] = 0;
			for(int j=0; j<nCriteria; j++) {
				ahpNormalizationAverage[i] += ahpNormalization[i][j];
			}
			ahpNormalizationAverage[i] /= nCriteria;
		}
		this.criteriaNormalizationAverage = ahpNormalizationAverage;
		for(int i=0; i<nCriteria; i++) {
			ahpWeightedSum[i]=0;
			for(int j=0; j<nCriteria;j++) {
				ahpWeightedSum[i] += criteriaValue[i][j]*ahpNormalizationAverage[j];
			}
		}
		this.criteriaWeightedSum = ahpWeightedSum;
		for(int i=0;i<nCriteria;i++) {
			ahpWeightedSumAverage[i] = ahpWeightedSum[i]/ahpNormalizationAverage[i];
		}
		this.criteriaWeightedSumAverage = ahpWeightedSumAverage;
		for(int i=0; i<nCriteria; i++) {
			ahpLambdaMax += ahpWeightedSumAverage[i];
		}
		ahpLambdaMax /= nCriteria;
		this.lamdaMax = ahpLambdaMax;
		for(int i=0;i<nCriteria;i++) {
			ahpCI = (ahpLambdaMax-nCriteria)/(nCriteria-1);
		}
		this.CI = ahpCI;
		ahpCR = ahpCI/crDivider[nCriteria-1];
		this.CR = ahpCR;
		
		if(ahpCR>0.1) {
			System.out.println("\n========================================================================================================\n"
							   + "Your CR is higher than 0.1! Please re-input your criteria value\n"
							   + "========================================================================================================");
			setCriteriaValue();
		}else if(ahpCR<0) {
			System.out.println("\n========================================================================================================\n"
							   + "Your CR is lower than 0! Please re-input your criteria value\n"
							   + "========================================================================================================");
			setCriteriaValue();
		}else {
			System.out.println("\nComparing Criteria: ");
			System.out.print("\n\t Criteria");
			for(int i = 0; i<this.nCriteria; i++) {
				if(criteriaName[i].length()<8) {
					System.out.print("\t");
				}
				System.out.print("\t" + criteriaName[i]);
			}
			for(int j=0; j<this.nCriteria; j++) {
				System.out.print("\n\t " + criteriaName[j]);
				if(criteriaName[j].length()<7) {
					System.out.print("\t");
				}
				for(int k=0; k<this.nCriteria;k++) {
					
					System.out.printf("\t\t%.2f", criteriaValue[j][k]);
				}
			}
		}
		

		
		
		
		for(int k=0; k<this.nCriteria;k++) {
			
		}
	}

	public float[] getCriteriaNormalizationAverage() {
		return criteriaNormalizationAverage;
	}

	public void setCriteriaNormalizationAverage(float[] criteriaNormalizationAverage) {
		this.criteriaNormalizationAverage = criteriaNormalizationAverage;
	}

	public float[] getCriteriaValueTotal() {
		return criteriaValueTotal;
	}

	public void setCriteriaValueTotal() {
		float[] ahpCriteriaTotal = new float[nCriteria];
		for(int i=0;i<nCriteria;i++) {
			ahpCriteriaTotal[i]=0;
		}
		for(int i=0;i<nCriteria;i++) {
			for(int j=0;j<nCriteria;j++){
				ahpCriteriaTotal[i] += criteriaValue[j][i];
			}
		}
		
		this.criteriaValueTotal = ahpCriteriaTotal;
	}

	public Criteria() {
		super();
	}

	public String[] getCriteriaName() {
		return criteriaName;
	}

	public void setCriteriaName() {
		String[] ahpCriteriaName = new String[nCriteria];
		Scanner sc = new Scanner(System.in);
		System.out.println("\n\nPlease insert your criteria names: ");
		int i = 0;
		do {
			System.out.print("\t Criteria "+ (i+1) +" name:");
			ahpCriteriaName[i] = sc.nextLine();
			
			i++;
		}while(i<nCriteria);
		
		this.criteriaName = ahpCriteriaName;
		
	}

	public int getnCriteria() {
		return nCriteria;
	}

	public void setnCriteria() {
		Scanner sc = new Scanner(System.in);
		System.out.print("\t Criteria (3-10): ");
		try {
			nCriteria = sc.nextInt();	
		}catch(Exception e) {
			System.out.println("\n\t Something went wrong! (" + e.toString() + ")");
			System.exit(0);
		}
		
		do{
			if(nCriteria>=3 && nCriteria<=10) {
				this.bCriteria = false;
			}else {
				System.out.println("\t *ERROR* Criteria is out of Range");
				System.out.print("\t Criteria (3-10): ");
				try {
					nCriteria = sc.nextInt();	
				}catch(Exception e) {
					System.out.println("\n\t Something went wrong! (" + e.toString() + ")");
					System.exit(0);
				}
				
			}
		}while(this.bCriteria);
	}

	public float[][] getCriteriaValue() {
		return criteriaValue;
	}

	public void setCriteriaValue() {
		Scanner sc = new Scanner(System.in);
		System.out.println("\nComparing Criteria: ");
		float[][] ahpCriteria = new float[nCriteria][nCriteria];
		System.out.print("\n\tCriteria");
		for(int i = 0; i<this.nCriteria; i++) {
			if(criteriaName[i].length()<8) {
				System.out.print("\t");
			}
			System.out.print("\t" + criteriaName[i]);
		}
		int x = 1;
		for(int j=0; j<this.nCriteria; j++) {
			System.out.print("\n\t " + criteriaName[j]);
			if(criteriaName[j].length()<7) {
				System.out.print("\t");
			}
			for(int k=0; k<this.nCriteria;k++) {
				
				if(j==k) {
					System.out.print("\t\t1");
				}else if(j>k) {
					System.out.print("\t\t" + "-");
				}else {
					System.out.print("\t\t" + "x-" + x);
					x++;
				}
			}
		}
		System.out.println("\n");
		x = 1;
		for(int j=0; j<this.nCriteria; j++) {
			for(int k=0; k<this.nCriteria;k++) {
				
				if(j==k) {
					ahpCriteria[j][k] = 1;
				}else if(j>k) {
					ahpCriteria[j][k] = ahpCriteria[k][k]/ahpCriteria[k][j];
				}else {
					System.out.print("\tWhat is the value of x-" + x + ": ");
					try {
						ahpCriteria[j][k] = sc.nextFloat();
					}catch(Exception e) {
						System.out.println("\n\t Something went wrong! (" + e.toString() + ")");
						System.exit(0);
					}
					x++;
				}
			}
		}
		
		this.criteriaValue = ahpCriteria;
		setCriteriaValueTotal();
		setCriteriaNormalization();
	}
	
	
	
	
	
	
	
	
	

}
