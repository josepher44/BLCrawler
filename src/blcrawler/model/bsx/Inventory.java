package blcrawler.model.bsx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

import blcrawler.model.bsx.inventorylot.DrawerDivision;
import blcrawler.model.bsx.inventorylot.InventoryLocation;
import blcrawler.model.bsx.inventorylot.InventoryLot;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory
{
	ObservableList<InventoryLot> inventoryLotList = FXCollections.observableArrayList();
	ObservableList<InventoryLocation> inventoryLocationList = FXCollections.observableArrayList();
	Hashtable<String, DrawerDivision> divisionTable;
	Hashtable<Integer, InventoryLocation>locationsBySequential;
	ArrayList<Integer> filledUndividedDrawers;
	ArrayList<Integer> emptyUndividedDrawers;
	ArrayList<DrawerDivision> divisionList;

	ArrayList<DrawerDivision> div1;
	ArrayList<DrawerDivision> div2;
	ArrayList<DrawerDivision> div3;
	ArrayList<DrawerDivision> div4;
	ArrayList<DrawerDivision> div5;
	ArrayList<DrawerDivision> div6;
	ArrayList<DrawerDivision> div7;
	ArrayList<DrawerDivision> div8;
	ArrayList<DrawerDivision> div9;
	ArrayList<DrawerDivision> div10;
	ArrayList<DrawerDivision> div11;
	ArrayList<DrawerDivision> div12;
	ArrayList<DrawerDivision> div13;
	ArrayList<DrawerDivision> div14;
	ArrayList<DrawerDivision> div15;
	ArrayList<DrawerDivision> div2x;
	ArrayList<DrawerDivision> div4x;
	ArrayList<DrawerDivision> div6x;
	ArrayList<DrawerDivision> div8x;
	ArrayList<DrawerDivision> div10x;

	ArrayList<InventoryLocation> undiv1;
	ArrayList<InventoryLocation> undiv2;
	ArrayList<InventoryLocation> undiv3;
	ArrayList<InventoryLocation> undiv4;
	ArrayList<InventoryLocation> undiv5;
	ArrayList<InventoryLocation> undiv6;
	ArrayList<InventoryLocation> undiv7;
	ArrayList<InventoryLocation> undiv8;
	ArrayList<InventoryLocation> undiv9;
	ArrayList<InventoryLocation> undiv10;
	ArrayList<InventoryLocation> undiv11;
	ArrayList<InventoryLocation> undiv12;
	ArrayList<InventoryLocation> undiv13;
	ArrayList<InventoryLocation> undiv14;
	ArrayList<InventoryLocation> undiv15;
	ArrayList<InventoryLocation> undiv16;
	ArrayList<InventoryLocation> undiv2x;
	ArrayList<InventoryLocation> undiv4x;
	ArrayList<InventoryLocation> undiv6x;
	ArrayList<InventoryLocation> undiv8x;
	ArrayList<InventoryLocation> undiv10x;

	int tobe1;
	int tobe2;
	int tobe3;
	int tobe4;
	int tobe5;
	int tobe6;
	int tobe7;
	int tobe8;
	int tobe9;
	int tobe10;
	int tobe11;
	int tobe12;
	int tobe13;
	int tobe14;
	int tobe15;
	int tobe2x;
	int tobe4x;
	int tobe6x;
	int tobe8x;
	int tobe10x;

	int tomake1;
	int tomake2;
	int tomake3;
	int tomake4;
	int tomake5;
	int tomake6;
	int tomake7;
	int tomake8;
	int tomake9;
	int tomake10;
	int tomake11;
	int tomake12;
	int tomake13;
	int tomake14;
	int tomake15;
	int tomake2x;
	int tomake4x;
	int tomake6x;
	int tomake8x;
	int tomake10x;


	public Inventory()
	{
		filledUndividedDrawers = new ArrayList<>();
		emptyUndividedDrawers = new ArrayList<>();
		locationsBySequential = new Hashtable<>();
		divisionTable = new Hashtable<>();
		divisionList = new ArrayList<>();

		div1 = new ArrayList<>();
		div2 = new ArrayList<>();
		div3 = new ArrayList<>();
		div4 = new ArrayList<>();
		div5 = new ArrayList<>();
		div6 = new ArrayList<>();
		div7 = new ArrayList<>();
		div8 = new ArrayList<>();
		div9 = new ArrayList<>();
		div10 = new ArrayList<>();
		div11 = new ArrayList<>();
		div12 = new ArrayList<>();
		div13 = new ArrayList<>();
		div14 = new ArrayList<>();
		div15 = new ArrayList<>();
		div2x = new ArrayList<>();
		div4x = new ArrayList<>();
		div6x = new ArrayList<>();
		div8x = new ArrayList<>();
		div10x = new ArrayList<>();

		undiv1 = new ArrayList<>();
		undiv2 = new ArrayList<>();
		undiv3 = new ArrayList<>();
		undiv4 = new ArrayList<>();
		undiv5 = new ArrayList<>();
		undiv6 = new ArrayList<>();
		undiv7 = new ArrayList<>();
		undiv8 = new ArrayList<>();
		undiv9 = new ArrayList<>();
		undiv10 = new ArrayList<>();
		undiv11 = new ArrayList<>();
		undiv12 = new ArrayList<>();
		undiv13 = new ArrayList<>();
		undiv14 = new ArrayList<>();
		undiv15 = new ArrayList<>();
		undiv16 = new ArrayList<>();
		undiv2x = new ArrayList<>();
		undiv4x = new ArrayList<>();
		undiv6x = new ArrayList<>();
		undiv8x = new ArrayList<>();
		undiv10x = new ArrayList<>();

	}

	public void mapLocationsToDivisions()
	{
		for (int i=0; i<inventoryLocationList.size(); i++)
		{
			if (divisionTable.containsKey(inventoryLocationList.get(i).getTrimmedRemarks()))
			{
				if (divisionTable.get(inventoryLocationList.get(i).getTrimmedRemarks()).getItems() == null)
				{
					divisionTable.get(inventoryLocationList.get(i).getTrimmedRemarks()).setItems(inventoryLocationList.get(i));
					//System.out.println("Drawer "+inventoryLocationList.get(i).getTrimmedRemarks()+" successfully paired");
				}
				else
				{
					System.out.println("ERROR: Duplicate entry found for drawer "+inventoryLocationList.get(i).getTrimmedRemarks());
				}
			}
			else
			{
				if (inventoryLocationList.get(i).getTrimmedRemarks().length()==9)
				{
					System.out.println("ERROR: No drawer found for "+inventoryLocationList.get(i).getTrimmedRemarks());
				}
			}

			inventoryLocationList.get(i).linkToDrawerDivision();
		}
		identifyEmptyDivisions();
		generateELists();
		drawersToMake();
	}

	public void identifyEmptyDivisions()
	{
		for (int i=0; i<divisionList.size(); i++)
		{
			DrawerDivision current = divisionList.get(i);
			String currentSize = divisionList.get(i).getSize();
			if( current.getItems() == null)
			{
				//System.out.println("Drawer number " + current.getRawRemarks()+" is empty, size "+current.getSize());
				switch (currentSize)
				{
			        case "1":  		div1.add(current);
			        break;
			        case "2":  		div2.add(current);
					break;
			        case "3": 	 	div3.add(current);
					break;
			        case "4": 	 	div4.add(current);
					break;
			        case "5":  		div5.add(current);
					break;
			        case "6":  		div6.add(current);
					break;
			        case "7":  		div7.add(current);
					break;
			        case "8": 	 	div8.add(current);
					break;
			        case "9":  		div9.add(current);
					break;
			        case "10":  	div10.add(current);
					break;
			        case "11":  	div11.add(current);
					break;
			        case "12":  	div12.add(current);
					break;
			        case "13":  	div13.add(current);
					break;
			        case "14":  	div14.add(current);
					break;
			        case "15":  	div15.add(current);
					break;
			        case "2x":  	div2x.add(current);
					break;
			        case "4x":  	div4x.add(current);
					break;
			        case "6x":  	div6x.add(current);
					break;
			        case "8x":  	div8x.add(current);
					break;
			        case "10x":  	div10x.add(current);
					break;
				}

			}



		}

		System.out.println("Empty 1: "+div1.size());
		System.out.println("Empty 2: "+div2.size());
		System.out.println("Empty 3: "+div3.size());
		System.out.println("Empty 4: "+div4.size());
		System.out.println("Empty 5: "+div5.size());
		System.out.println("Empty 6: "+div6.size());
		System.out.println("Empty 7: "+div7.size());
		System.out.println("Empty 8: "+div8.size());
		System.out.println("Empty 9: "+div9.size());
		System.out.println("Empty 10: "+div10.size());
		System.out.println("Empty 11: "+div11.size());
		System.out.println("Empty 12: "+div12.size());
		System.out.println("Empty 13: "+div13.size());
		System.out.println("Empty 14: "+div14.size());
		System.out.println("Empty 15: "+div15.size());
		System.out.println("Empty 2x: "+div2x.size());
		System.out.println("Empty 4x: "+div4x.size());
		System.out.println("Empty 6x: "+div6x.size());
		System.out.println("Empty 8x: "+div8x.size());
		System.out.println("Empty 10x: "+div10x.size());




	}

	public void subtractToBe(int size, int value)
	{
		//System.out.println("SubtractToBe called for size "+size);
		switch(size)
		{
		case 1: tobe1 = tobe1-value;
		break;
		case 2: tobe2 = tobe2-value;
		break;
		case 3: tobe3 = tobe3-value;
		break;
		case 4: tobe4 = tobe4-value;
		break;
		case 5: tobe5 = tobe5-value;
		break;
		case 6: tobe6 = tobe6-value;
		break;
		case 7: tobe7 = tobe7-value;
		break;
		case 8: tobe8 = tobe8-value;
		break;
		case 9: tobe9 = tobe9-value;
		break;
		case 10: tobe10 = tobe10-value;
		break;
		case 11: tobe11 = tobe11-value;
		break;
		case 12: tobe12 = tobe12-value;
		break;
		case 13: tobe13 = tobe13-value;
		break;
		case 14: tobe14 = tobe14-value;
		break;
		case 15: tobe15 = tobe15-value;
		//default: return 0;
		}
	}

	public int toBe(int size)
	{
		switch(size)
		{
		case 1: return tobe1;
		case 2: return tobe2;
		case 3: return tobe3;
		case 4: return tobe4;
		case 5: return tobe5;
		case 6: return tobe6;
		case 7: return tobe7;
		case 8: return tobe8;
		case 9: return tobe9;
		case 10: return tobe10;
		case 11: return tobe11;
		case 12: return tobe12;
		case 13: return tobe13;
		case 14: return tobe14;
		case 15: return tobe15;
		default: return 0;
		}
	}

	public void drawersToMake()
	{
		tobe1=undiv1.size()-div1.size();
		tobe2=undiv2.size()-div2.size();
		tobe3=undiv3.size()-div3.size();
		tobe4=undiv4.size()-div4.size();
		tobe5=undiv5.size()-div5.size();
		tobe6=undiv6.size()-div6.size();
		tobe7=undiv7.size()-div7.size();
		tobe8=undiv8.size()-div8.size();
		tobe9=undiv9.size()-div9.size();
		tobe10=undiv10.size()-div10.size();
		tobe11=undiv11.size()-div11.size();
		tobe12=undiv12.size()-div12.size();
		tobe13=undiv13.size()-div13.size();
		tobe14=undiv14.size()-div14.size();
		tobe15=undiv15.size()-div15.size();
		tobe2x=undiv2x.size()-div2x.size();
		tobe4x=undiv4x.size()-div4x.size();
		tobe6x=undiv6x.size()-div6x.size();
		tobe8x=undiv8x.size()-div8x.size();
		tobe10x=undiv10x.size()-div10x.size();

		tomake2x = tobe2x/2;
		tomake4x = tobe4x/4;
		tomake6x = tobe6x/6;
		tomake8x = tobe8x/8;
		tomake10x = tobe10x/10;

		if (tobe2x%2!=0&&tobe2x>0)
			tomake2x++;
		if (tobe4x%4!=0&&tobe4x>0)
			tomake4x++;
		if (tobe6x%6!=0&&tobe6x>0)
			tomake6x++;
		if (tobe8x%8!=0&&tobe8x>0)
			tomake8x++;
		if (tobe10x%10!=0&&tobe10x>0)
			tomake10x++;








		System.out.println("2x: "+tomake2x);
		System.out.println("4x: "+tomake4x);
		System.out.println("6x: "+tomake6x);
		System.out.println("8x: "+tomake8x);
		System.out.println("10x: "+tomake10x);

		ArrayList<ArrayList<Integer>> drawersToBeMade = new ArrayList<>();

		for (int i=16;i>0;i--)
		{
			System.out.println("Working on drawers of size "+i+", count of "+toBe(i));
			//execute this loop for each primary division size, starting with 15
			//for (int j=0;j<toBe(i);j++)
			while(toBe(i)>0)
			{
				//System.out.println("Top level loop index (j) value: "+j);
				//System.out.println("toBeMade count for top level loop: "+toBe(i));
				//execute this loop for however many drawers to be made of size i there are
				int left = 16-i;
				ArrayList<Integer> divisions = new ArrayList<>();
				divisions.add(i);
				subtractToBe(i,1);
				divisions.addAll(combinatorics(left));
				String output="";
				for (int k=0;k<divisions.size();k++)
				{
					output=output+divisions.get(k)+" ";
				}
				System.out.println("Created a drawer with divisions "+output);
				drawersToBeMade.add(divisions);
			}
		}

		System.out.println("Total drawers to be glued: "+(drawersToBeMade.size()+tomake2x+tomake4x+tomake6x+tomake8x+tomake10x));
	}

	public ArrayList<Integer> combinatorics(int sizeToFill)
	{
		//System.out.println("Started a new combinatorics instance for size "+sizeToFill);
		for (int i=sizeToFill;i>0;i--)
		{
			//execute this loop for each primary division size i, starting with the value to be filled
			if (toBe(i)>0)
				{
				if (i==sizeToFill)
				{
					ArrayList<Integer> returnValue = new ArrayList<>();
					returnValue.add(i);
					subtractToBe(i,1);
					//System.out.println("Subtracted a drawer of size "+i+ " in combinatorics path A. "+toBe(i)+
					//		" remaining.");
					return returnValue;

				}
				else
				{
					ArrayList<Integer> returnValue = new ArrayList<>();
					returnValue.add(i);
					subtractToBe(i,1);
					//System.out.println("Subtracted a drawer of size "+i+ " in combinatorics path B. "+toBe(i)+
					//		" remaining.");
					int left = sizeToFill-i;
					returnValue.addAll(combinatorics(left));
					return returnValue;
				}
			}
		}
		//Code will not be reached unless all lists of sizeToFill and under are empty
		ArrayList<Integer> returnValue = new ArrayList<>();
		returnValue.add(sizeToFill);
		subtractToBe(sizeToFill,1);
		//System.out.println("Subtracted a drawer of size "+sizeToFill+ " in combinatorics path C. "+toBe(sizeToFill)+
		//		" remaining.");
		//System.out.println("Creating an extra compartment of size "+sizeToFill);
		return returnValue;


	}




	public void generateELists()
	{
		for (int i=0; i<inventoryLocationList.size(); i++)
		{
			try
			{
				InventoryLocation current = inventoryLocationList.get(i);
				if (!divisionTable.containsKey(current.getTrimmedRemarks()))
				{
					if (current.getEcode()!=null&&current.getCabinet()<25)
					{


						String ecode = current.getEcode();
						switch (ecode)
						{
					        case "1":  		undiv1.add(current);
					        break;
					        case "2":  		undiv2.add(current);
							break;
					        case "3": 	 	undiv3.add(current);
							break;
					        case "4": 	 	undiv4.add(current);
							break;
					        case "5":  		undiv5.add(current);
							break;
					        case "6":  		undiv6.add(current);
							break;
					        case "7":  		undiv7.add(current);
							break;
					        case "8": 	 	undiv8.add(current);
							break;
					        case "9":  		undiv9.add(current);
							break;
					        case "10":  	undiv10.add(current);
							break;
					        case "11":  	undiv11.add(current);
							break;
					        case "12":  	undiv12.add(current);
							break;
					        case "13":  	undiv13.add(current);
							break;
					        case "14":  	undiv14.add(current);
							break;
					        case "15":  	undiv15.add(current);
							break;
					        case "16": 		undiv16.add(current);
					        break;
					        case "2x":  	undiv2x.add(current);
							break;
					        case "4x":  	undiv4x.add(current);
							break;
					        case "6x":  	undiv6x.add(current);
							break;
					        case "8x":  	undiv8x.add(current);
							break;
					        case "10x":  	undiv10x.add(current);
							break;
					        default: //System.out.println("Invalid E Code "+current.getEcode()+ " at drawer "+current.getRemarks());
					        break;
						}
					}
					else
					{
						//System.out.println("Invalid E Code "+current.getEcode()+ " at drawer "+current.getRemarks());
					}
				}
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				System.out.println("Error at drawer "+inventoryLocationList.get(i).getRemarks());
			}
		}

		System.out.println("Expected 1: "+undiv1.size());
		System.out.println("Expected 2: "+undiv2.size());
		System.out.println("Expected 3: "+undiv3.size());
		System.out.println("Expected 4: "+undiv4.size());
		System.out.println("Expected 5: "+undiv5.size());
		System.out.println("Expected 6: "+undiv6.size());
		System.out.println("Expected 7: "+undiv7.size());
		System.out.println("Expected 8: "+undiv8.size());
		System.out.println("Expected 9: "+undiv9.size());
		System.out.println("Expected 10: "+undiv10.size());
		System.out.println("Expected 11: "+undiv11.size());
		System.out.println("Expected 12: "+undiv12.size());
		System.out.println("Expected 13: "+undiv13.size());
		System.out.println("Expected 14: "+undiv14.size());
		System.out.println("Expected 15: "+undiv15.size());
		System.out.println("Expected 2x: "+undiv2x.size());
		System.out.println("Expected 4x: "+undiv4x.size());
		System.out.println("Expected 6x: "+undiv6x.size());
		System.out.println("Expected 8x: "+undiv8x.size());
		System.out.println("Expected 10x: "+undiv10x.size());

		System.out.println("To be made 1: "+(undiv1.size()-div1.size()));
		System.out.println("To be made 2: "+(undiv2.size()-div2.size()));
		System.out.println("To be made 3: "+(undiv3.size()-div3.size()));
		System.out.println("To be made 4: "+(undiv4.size()-div4.size()));
		System.out.println("To be made 5: "+(undiv5.size()-div5.size()));
		System.out.println("To be made 6: "+(undiv6.size()-div6.size()));
		System.out.println("To be made 7: "+(undiv7.size()-div7.size()));
		System.out.println("To be made 8: "+(undiv8.size()-div8.size()));
		System.out.println("To be made 9: "+(undiv9.size()-div9.size()));
		System.out.println("To be made 10: "+(undiv10.size()-div10.size()));
		System.out.println("To be made 11: "+(undiv11.size()-div11.size()));
		System.out.println("To be made 12: "+(undiv12.size()-div12.size()));
		System.out.println("To be made 13: "+(undiv13.size()-div13.size()));
		System.out.println("To be made 14: "+(undiv14.size()-div14.size()));
		System.out.println("To be made 15: "+(undiv15.size()-div15.size()));
		System.out.println("To be made 2x: "+(undiv2x.size()-div2x.size()));
		System.out.println("To be made 4x: "+(undiv4x.size()-div4x.size()));
		System.out.println("To be made 6x: "+(undiv6x.size()-div6x.size()));
		System.out.println("To be made 8x: "+(undiv8x.size()-div8x.size()));
		System.out.println("To be made 10x: "+(undiv10x.size()-div10x.size()));

		System.out.println("Full drawers: "+undiv16.size());






	}



	public ArrayList<DrawerDivision> getDivisionList()
	{
		return divisionList;
	}

	public void setDivisionList(ArrayList<DrawerDivision> divisionList)
	{
		this.divisionList = divisionList;
	}

	public Hashtable<String, DrawerDivision> getDivisionTable()
	{
		return divisionTable;
	}

	public void setDivisionTable(Hashtable<String, DrawerDivision> divisionTable)
	{
		this.divisionTable = divisionTable;
	}

	public void identifyEmptyUndividedDrawers()
	{
		for (int i=0; i<inventoryLocationList.size(); i++)
		{
			String rawRemarks = inventoryLocationList.get(i).getRemarks();
			try
			{
				rawRemarks = rawRemarks.substring(0, rawRemarks.indexOf('~'));
				//inventoryLocationList.get(i).setTrimmedRemarks(rawRemarks);
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println("Remarks Trim Exception at drawer "+rawRemarks);
			}
			if (rawRemarks.length() == 6 || !(rawRemarks.matches("[1234567890-]+rem")))
			{
				inventoryLocationList.get(i).setDivided(false);
				if (rawRemarks.matches("[1234567890-]+rem"))
					{
						if (filledUndividedDrawers.contains(sequentialFromLabeled(rawRemarks)))
						{
							System.out.println("Warning: Duplicate entry at " +rawRemarks);
						}
						filledUndividedDrawers.add(sequentialFromLabeled(rawRemarks));
					}


				inventoryLocationList.get(i).setTrimmedRemarks(rawRemarks);
				try
				{
					inventoryLocationList.get(i).setNormalizedDrawerNumber(sequentialFromLabeled(rawRemarks));
				}
				catch (Exception e)
				{
				}
				System.out.println ("Undivided: "+rawRemarks);

			}
			else
			{
				inventoryLocationList.get(i).setDivided(true);
				System.out.println ("Divided: "+rawRemarks);
			}
		}

		/*System.out.println("Inventory Location list size is: "+inventoryLocationList.size());
		for (int k=0; k<inventoryLocationList.size(); k++)
		{
			inventoryLocationList.get(k).setNormalizedDrawerNumber(sequentialFromLabeled
					(inventoryLocationList.get(k).getTrimmedRemarks()));
			locationsBySequential.put(inventoryLocationList.get(k).getNormalizedDrawerNumber(),
					inventoryLocationList.get(k));
			System.out.println("Loop iteration"+k+": Put "+inventoryLocationList.get(k).getNormalizedDrawerNumber()
					+", "+ inventoryLocationList.get(k).getTrimmedRemarks());
		}
		*/
		identifyEmptyFullSizeDrawers(449, sequentialFromLabeled("020-39"));
		identifyLastFull();





		//clearEmptiesFromRight();





	}

	public void identifyEmptyFullSizeDrawers(int divisionStart, int end)
	{
		Collections.sort(filledUndividedDrawers);
		for (int i=divisionStart; i<end+1; i++)
		{
			if (!filledUndividedDrawers.contains(i))
			{
				//System.out.println ("Drawer #" +i+ " is empty.");
				System.out.println ("Drawer #" +labeledFromSequential(i)+ " is empty");
				emptyUndividedDrawers.add(i);
			}
		}
	}

	public int identifyLastFull()
	{
		for (int i=1440; i>1; i--)
		{
			if (filledUndividedDrawers.contains(i))
			{
				System.out.println("Last full drawer is" + labeledFromSequential(i));
				return i;
			}
		}
		return 1;
	}

	public void clearEmptiesFromRight()
	{
		Collections.sort(emptyUndividedDrawers);
		for (int i=0; i<emptyUndividedDrawers.size(); i++)
		{
			String oldRemarks = labeledFromSequential(emptyUndividedDrawers.get(i));
			System.out.println(oldRemarks);
			int sequentialValue = emptyUndividedDrawers.get(i);
			System.out.println(sequentialValue);
			InventoryLocation location = locationsBySequential.get(sequentialValue);
			System.out.println(location);
			String labeled = labeledFromSequential(sequentialValue);
			try
			{
				location.setRemarks(labeled);
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Set drawer" + oldRemarks + " to " + labeledFromSequential(emptyUndividedDrawers.get(i)));
		}


	}

	public void identifyDuplicates()
	{

		for (int i=0; i<filledUndividedDrawers.size(); i++)
		{

		}
	}

	public int sequentialFromLabeled(String labeled)
	{

		try
		{
			String cabinet = labeled.substring(0, 3);
			String drawer = labeled.substring(4,6);
			int output = Integer.parseInt(cabinet)*60+Integer.parseInt(drawer);

			System.out.println("Drawer #" + labeled + "Converted to Drawer #" + output);
			return output;
		}
		catch (Exception e)
		{
			System.out.println("sequential from labeled exception for labeled value "+labeled);
		}
		return 0;
	}

	public String labeledFromSequential(int labeled)
	{
		String outputCabinet;
		String outputDrawer;
		int cabinet = labeled/60;
		int drawer = labeled % 60;
		if (drawer == 0)
		{
			drawer = 60;
		}
		if (cabinet<10)
		{
			outputCabinet = "00"+String.valueOf(cabinet);
		}
		else
		{
			outputCabinet = "0"+String.valueOf(cabinet);
		}
		if (drawer<10)
		{
			outputDrawer = "0"+String.valueOf(drawer);
		}
		else
		{
			outputDrawer = String.valueOf(drawer);
		}
		return outputCabinet+"-"+outputDrawer;
	}





















	public ObservableList<InventoryLot> getInventoryLotList()
	{
		return inventoryLotList;
	}

	public void setInventoryLotList(ObservableList<InventoryLot> inventoryLotList)
	{
		this.inventoryLotList = inventoryLotList;
	}

	public ObservableList<InventoryLocation> getInventoryLocationList()
	{
		return inventoryLocationList;
	}

	public void setInventoryLocationList(ObservableList<InventoryLocation> inventoryLocationList)
	{
		this.inventoryLocationList = inventoryLocationList;

	}


}
