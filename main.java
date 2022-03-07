package com.example.dssMidtermV1;

import java.util.Arrays;
import java.util.Scanner;

public class main {
	

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String ahpItem;
		Criteria criteria = new Criteria();
		Alternative alternative = new Alternative();

//===========asking for item name====================================================
		
		Scanner sc = new Scanner(System.in);
		System.out.println("\n========================================================================================================\n"
						   + "\t\t\t\t     Welcome to AHP Calculator!\n"
						   + "========================================================================================================\n");
		System.out.print("What item do you want to compare: ");
		ahpItem = sc.nextLine();
		
//==========asking for how many criteria and alternative==============================================
		
		System.out.println("\nPlease input how many '" + ahpItem + "' criteria and alternative:");
		criteria.setnCriteria();
		alternative.setnAlternative();
		System.out.print("========================================================================================================\n"
					   + "\t\t\t Please input your criteria and alternative name!\n"
					   + "========================================================================================================");
//==========asking for criteria name==============================================
		
		criteria.setCriteriaName();
		
//==========asking for alternative name===========================================

		alternative.setAlternativeName();
		
//==========inputing criteria comparing===========================================
		System.out.print("\n========================================================================================================\n"
					     + "\t\t\t\t Please input your criteria value!\n"
					     + "========================================================================================================\n");
		criteria.setCriteriaValue();
		
//==========inputing alternative comparing========================================
		System.out.print("\n\n========================================================================================================\n"
					       + "\t\t\t\t Please input your alternative value!\n"
					       + "========================================================================================================\n");
		alternative.setnCriteria(criteria.getnCriteria());
		alternative.setCriteriaName(criteria.getCriteriaName());
		alternative.setAlternativeValue();
		
//==========calculating final decision============================================
		System.out.print("\n\n========================================================================================================\n"
					       + "\t\t\t\t\t DSS Result!\n"
					       + "========================================================================================================\n\n");
		float[][] alternativeNormalizationAverage = alternative.getAlternativeNormalizationAverage();
		float[] criteriaNormalizationAverage = criteria.getCriteriaNormalizationAverage();
		float[]	finalDssValue = new float[alternative.getnAlternative()];
		
		for(int i=0;i<alternative.getnAlternative();i++) {
			finalDssValue[i] = 0;
			for(int j=0;j<criteria.getnCriteria();j++) {
				finalDssValue[i] += alternativeNormalizationAverage[j][i]*criteriaNormalizationAverage[j];
			}
		}
		float maxDss = 0;
		int maxDssPosition = 0;
		for(int i=0;i<alternative.getnAlternative();i++) {
			if(finalDssValue[i]>maxDss) {
				maxDss = finalDssValue[i];
				maxDssPosition = i;
			}
		}
		String[] dssName = alternative.getAlternativeName();
		System.out.println("\t\tRank of '" + ahpItem + "'");
		for(int i=0;i<alternative.getnAlternative();i++) {
			System.out.print("\t\t " + dssName[i] + "\t");
			if(dssName[i].length()<8) {
				System.out.print("\t");
			}
			System.out.printf("%.3f",finalDssValue[i]);
			if(maxDssPosition==i) {
				System.out.println("\t BEST");
			}else {
				System.out.print("\n");
			}
			
		}

		
		System.out.println("\n Do you want to see the normalization table: ");
		System.out.println("\t 1. Yes");
		System.out.println("\t 2. No");
		System.out.print("\n\tAnswer: ");
		String process = sc.next();
		if(process.equals("1")) {
			System.out.print("\n\n========================================================================================================\n"
					       + "\t\t\t\t   Normalization Table\n"
					       + "========================================================================================================\n\n");
			criteria.printNormalization();
			alternative.printNormalization();
			System.out.print("\n\n========================================================================================================\n"
					       + "\t\t\t\t Thank you for using 'AHP Calculator'!\n"
					       + "========================================================================================================\n\n");
		}else if(process.equals("2")) {
			System.out.print("\n\n========================================================================================================\n"
						       + "\t\t\t\t Thank you for using 'AHP Calculator'!\n"
						       + "========================================================================================================\n\n");
		}else {
			System.out.println("You inputed the wrong format! we assumed you choose no");
			System.out.print("\n\n========================================================================================================\n"
						       + "\t\t\t\t Thank you for using 'AHP Calculator'!\n"
						       + "========================================================================================================\n\n");
		}
		
	}


	

	

}
