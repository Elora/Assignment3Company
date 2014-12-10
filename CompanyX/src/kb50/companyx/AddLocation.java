package kb50.companyx;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddLocation extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	//	setContentView(R.layout.activity_add_location);

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