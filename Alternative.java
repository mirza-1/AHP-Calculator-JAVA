package com.example.dssMidtermV1;

import java.util.Scanner;

public class Alternative {
	String[] alternativeName, criteriaName;
	float[][][] alternativeValue, alternativeNormalization;
	int nAlternative, nCriteria;
	boolean bAlternative=true;
	float[][] alternativeValueTotal, alternativeNormalizationAverage, alternativeWeightedSum, alternativeWeightedSumAverage;
	float[] lamdaMax, CI, CR, criteriaWeightedSumAverage,crDivider = {0f,0f,0.58f,0.9f,1.12f,1.24f,1.32f,1.41f,1.45f,1.49f};

	
	public Alternative() {
		super();
	}

	public float[][] getAlternativeNormalizationAverage() {
		return alternativeNormalizationAverage;
	}

	public void setAlternativeNormalizationAverage(float[][] alternativeNormalizationAverage) {
		this.alternativeNormalizationAverage = alternativeNormalizationAverage;
	}

	public String[] getAlternativeName() {
		return alternativeName;
	}

	public void setAlternativeName() {
		String[] ahpAlternativeName = new String[nAlternative];
		Scanner sc = new Scanner(System.in);
		System.out.println("\n\nPlease insert your alternative names: ");
		int i = 0;
		do {
			System.out.print("\t alternative "+ (i+1) +" name:");
			ahpAlternativeName[i] = sc.nextLine();
			
			i++;
		}while(i<nAlternative);
		
		this.alternativeName = ahpAlternativeName;
		
	}

	public int getnAlternative() {
		return nAlternative;
	}

	public void setnAlternative() {
		Scanner sc = new Scanner(System.in);
		System.out.print("\t Alternative (3-6): ");
		try {
			nAlternative = sc.nextInt();
			System.out.print("\n");
		}catch(Exception e) {
			System.out.println("\n\t Something went wrong! (" + e.toString() + ")");
			System.exit(0);
		}
		do{
			if(nAlternative>=3 && nAlternative<=6) {
				this.bAlternative = false;
			}else {
				System.out.println("\t *ERROR* Alternative is out of Range");
				System.out.print("\t Alternative (3-6): ");
				try {
					nAlternative = sc.nextInt();
					System.out.print("\n");
				}catch(Exception e) {
					System.out.println("\n\t Something went wrong! (" + e.toString() + ")");
					System.exit(0);
				}
			}
		}while(this.bAlternative);
	}

	public float[][][] getAlternativeValue() {
		return alternativeValue;
	}
	
	public void printNormalization() {
		for(int i=0;i<this.nCriteria;i++) {
			System.out.print("\n\n\t"+criteriaName[i]+"\t");
			if(criteriaName[i].length()<8) {
				System.out.print("\t");
			}
			for(int j=0;j<nAlternative;j++) {
				System.out.print("\t" + alternativeName[j]);
				if(alternativeName[j].length()<7) {
					System.out.print("\t");
				}
			}
			System.out.print("\tAverage");
			System.out.print("\t\tWeighted_Sum\t");
			System.out.print("\tWS/AVG");
			for(int j=0; j<this.nAlternative; j++) {
				System.out.print("\n\t " + alternativeName[j]);
				if(alternativeName[j].length()<7) {
					System.out.print("\t");
				}
				for(int k=0; k<this.nAlternative;k++) {
					System.out.printf("\t\t%.2f", alternativeNormalization[i][j][k]);
				}
				System.out.printf("\t\t%.2f", alternativeNormalizationAverage[i][j]);
				System.out.printf("\t\t%.2f", alternativeWeightedSum[i][j]);
				System.out.printf("\t\t\t%.2f", alternativeWeightedSumAverage[i][j]);
				if(j==0) {
					System.out.printf("\t\tLambda_Max: \t%.3f", lamdaMax[i]);
				}else if(j==1) {
					System.out.printf("\t\tCI: \t\t%.3f", CI[i]);
				}else if(j==2) {
					System.out.printf("\t\tCR: \t\t%.3f", CR[i]);
				}
			}
		}
	}

	public void setAlternativeValue() {
		float[][][] ahpAlternative = new float[nCriteria][nAlternative][nAlternative];
		float[][] ahpAlternativeTotal = new float[nCriteria][nAlternative];
		float[][][] ahpNormalization = new float [nCriteria][nAlternative][nAlternative];
		float[][] ahpNormalizationAverage = new float[nCriteria][nAlternative], ahpWeightedSum = new float[nCriteria][nAlternative], ahpWeightedSumAverage = new float[nCriteria][nAlternative];
		float[] ahpLambdaMax = new float[nCriteria], ahpCI = new float[nCriteria], ahpCR = new float[nCriteria];
		Scanner sc = new Scanner(System.in);
		System.out.println("\nComparing Alternative: ");
		int x = 1;
		for(int i=0; i<this.nCriteria; i++) {
			System.out.println("\n\t\t"+criteriaName[i]);
			System.out.println("\t=============================================================\n");
			System.out.print("\t " + criteriaName[i]);
			if(criteriaName[i].length()<7) {
				System.out.print("\t");
			}
			for(int j = 0; j<this.nAlternative; j++) {
				if(alternativeName[j].length()<8) {
					System.out.print("\t");
				}
				System.out.print("\t" + alternativeName[j]);
			}
			x = 1;
			for(int j=0; j<this.nAlternative; j++) {
				System.out.print("\n\t " + alternativeName[j]);
				if(alternativeName[j].length()<7) {
					System.out.print("\t");
				}
				for(int k=0; k<this.nAlternative;k++) {
					
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
			for(int j=0; j<this.nAlternative; j++) {
				for(int k=0; k<this.nAlternative;k++) {
					
					if(j==k) {
						ahpAlternative[i][j][k] = 1;
					}else if(j>k) {
						ahpAlternative[i][j][k] = ahpAlternative[i][k][k]/ahpAlternative[i][k][j];
					}else {
						System.out.print("\tWhat is the value of x-" + x + ": ");
						try {
							ahpAlternative[i][j][k] = sc.nextFloat();
						}catch(Exception e) {
							System.out.println("\n\t Something went wrong! (" + e.toString() + ")");
							System.exit(0);
						}
						x++;
					}
				}
			}
			

			for(int j=0;j<nAlternative;j++) {
				ahpAlternativeTotal[i][j]=0;
			}
			for(int j=0;j<nAlternative;j++) {
				for(int k=0;k<nAlternative;k++){
					ahpAlternativeTotal[i][j] += ahpAlternative[i][k][j];
				}
			}
			
			this.alternativeValueTotal = ahpAlternativeTotal;
			

			for(int j=0;j<nAlternative;j++) {
				for(int k=0;k<nAlternative;k++) {
					ahpNormalization[i][j][k] = ahpAlternative[i][j][k]/ahpAlternativeTotal[i][k];
				}
			}
			this.alternativeNormalization = ahpNormalization;
			for(int j=0; j<nAlternative;j++) {
				ahpNormalizationAverage[i][j] = 0;
				for(int k=0; k<nAlternative; k++) {
					ahpNormalizationAverage[i][j] += ahpNormalization[i][j][k];
				}
				ahpNormalizationAverage[i][j] /= nAlternative;
			}
			this.alternativeNormalizationAverage = ahpNormalizationAverage;
			for(int j=0; j<nAlternative; j++) {
				ahpWeightedSum[i][j]=0;
				for(int k=0; k<nAlternative;k++) {
					ahpWeightedSum[i][j] += ahpAlternative[i][j][k]*ahpNormalizationAverage[i][k];
				}
			}
			this.alternativeWeightedSum = ahpWeightedSum;
			for(int j=0;j<nAlternative;j++) {
				ahpWeightedSumAverage[i][j] = ahpWeightedSum[i][j]/ahpNormalizationAverage[i][j];
			}
			this.alternativeWeightedSumAverage = ahpWeightedSumAverage;
			ahpLambdaMax[i] = 0;
			for(int j=0; j<nAlternative; j++) {
				ahpLambdaMax[i] += ahpWeightedSumAverage[i][j];
			}
			ahpLambdaMax[i] /= nAlternative;
			this.lamdaMax = ahpLambdaMax;
			for(int j=0;j<nAlternative;j++) {
				ahpCI[i] = (ahpLambdaMax[i]-nAlternative)/(nAlternative-1);
			}
			this.CI = ahpCI;
			ahpCR[i] = ahpCI[i]/crDivider[nAlternative-1];
			this.CR = ahpCR;
			



			if(ahpCR[i]>0.1) {
				System.out.println("========================================================================================================\n"
								 + "Your CR is higher than 0.1! Please re-input your criteria value\n"
								 + "========================================================================================================\n");
				i--;
			}else if(ahpCR[i]<0){
				System.out.println("========================================================================================================\n"
								 + "Your CR is lower than 0! Please re-input your criteria value\n"
								 + "========================================================================================================\n");
				i--;
			}else {
				System.out.print("\n\t " + criteriaName[i]);
				if(criteriaName[i].length()<7) {
					System.out.print("\t");
				}
				for(int j = 0; j<this.nAlternative; j++) {
					if(alternativeName[j].length()<8) {
						System.out.print("\t");
					}
					System.out.print("\t" + alternativeName[j]);
				}
				for(int j=0; j<this.nAlternative; j++) {
					System.out.print("\n\t " + alternativeName[j]+"\t");
					if(alternativeName[j].length()<7) {
						System.out.print("\t");
					}
					for(int k=0; k<this.nAlternative;k++) {
						System.out.print("\t"+ahpAlternative[i][j][k]+"\t");
					}
				}
				System.out.println("\n");
			}
			
		}
		
		
		
		this.alternativeValue = ahpAlternative;
	}

	public String[] getCriteriaName() {
		return criteriaName;
	}

	public void setCriteriaName(String[] criteriaName) {
		this.criteriaName = criteriaName;
	}

	public int getnCriteria() {
		return nCriteria;
	}

	public void setnCriteria(int nCriteria) {
		this.nCriteria = nCriteria;
	}
	
	
	

}
