package kb50.companyx;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class CompanyList extends ListActivity {

	List<Company> companyList;
	String[] companies; 
	
	public void stringToArr() {

		companyList = getAllCompanies();
		companies = new String[companyList.size()];
		for (int i = 0; i < companyList.size(); i++) {

			companies[i] = getAllCompanies().get(i).getName();

		}

	}

	public List<Company> getAllCompanies() {
		List<Company> companies = new ArrayList<Company>();

		Cursor cursor = getContentResolver()
				.query(Uri
						.parse("content://com.example.appcontentprovider.CompanyProvider/company"),
						null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Company company = new Company();

			company.setId(cursor.getInt(0));
			company.setName(cursor.getString(1));
			company.setInfo(cursor.getString(2));
			company.setSite(cursor.getString(3));
			companies.add(company);
			cursor.moveToNext();
		}

		cursor.close();
		return companies;

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); // ---no need to call this---
		Company c = new Company();
		c.setInfo("some commpany info");
		c.setName("Google inc");
		c.setSite("Google.com");
		
		
		ContentValues companyValues = new ContentValues();
		companyValues.put("name", c.getName());
		companyValues.put("website", c.getSite());
		companyValues.put("info", c.getInfo());
		
		getContentResolver().insert(Uri.parse("content://com.example.appcontentprovider.CompanyProvider/company"), companyValues);
		stringToArr();// //setContentView(R.layout.main);
		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, companies));
	}

	@Override
	public void onListItemClick(ListView parent, View v, int position, long id) {
		Toast.makeText(this, "You have selected " + companies[position],
				Toast.LENGTH_SHORT).show();
	}

}
