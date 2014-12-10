package kb50.companyx;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddCompany extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_company);

	}

	public void onClick(View view) {

		Company c = new Company();
		c.setName(((EditText) findViewById(R.id.nameField)).getText()
				.toString());
		c.setSite(((EditText) findViewById(R.id.websiteField)).getText()
				.toString());
		c.setInfo(((EditText) findViewById(R.id.infoField)).getText()
				.toString());

		ContentValues values = new ContentValues();
		values.put("name", c.getName());
		values.put("website", c.getSite());
		values.put("info", c.getInfo());

		try {
			getContentResolver()
					.insert(Uri
							.parse("content://com.example.appcontentprovider.CompanyProvider/company"),
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