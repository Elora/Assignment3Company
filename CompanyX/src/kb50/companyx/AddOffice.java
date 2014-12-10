package kb50.companyx;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class AddOffice extends Activity {
	private List<Company> companyList;
	private String[] companies; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_office);

		stringToArr();
		ListView l = (ListView)findViewById(R.id.officeList);
		//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_2,companies);
		
		
		l.setAdapter(new ArrayAdapter<String>(this,
				R.id.textView2, companies));
	}
	
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

	public void onClick(View view) {

		Location l = new Location();
		l.setCountry(((EditText) findViewById(R.id.countryField)).getText()
				.toString());
		l.setCity(((EditText) findViewById(R.id.cityField)).getText()
				.toString());
		l.setAdress(((EditText) findViewById(R.id.addressField)).getText()
				.toString());

		ContentValues values = new ContentValues();
		values.put("name", l.getCountry());
		values.put("website", l.getCity());
		values.put("info", l.getAdress());

		try {
			getContentResolver()
					.insert(Uri
							.parse("content://com.example.appcontentprovider.CompanyProvider/location"),
							values);
			
			Toast toast = Toast.makeText(getApplicationContext(), "Added succesfully!",Toast.LENGTH_SHORT);
			toast.show();
			
		} catch (Exception e) {
			Toast toast = Toast.makeText(getApplicationContext(), "Failed to add!",Toast.LENGTH_SHORT);
			toast.show();
			e.printStackTrace();
		}

		Intent i = new Intent(this,CompanyList.class);
		startActivity(i);

	}
}