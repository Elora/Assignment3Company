package kb50.companyx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	public void onClick(View view) {

		if (view.getId() == R.id.companiesButton) {

			startActivity(new Intent(this, CompanyList.class));

		}
		if (view.getId() == R.id.officesButton) {

			startActivity(new Intent(this, OfficeList.class));

		}
		if (view.getId() == R.id.locationsButton) {

			startActivity(new Intent(this, LocationList.class));

		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);

		int id = item.getItemId();
		if (id == R.id.add_company) {
			startActivity(new Intent(this, AddCompany.class));
		}
		if (id == R.id.add_location) {
			startActivity(new Intent(this, AddLocation.class));
		}
		if (id == R.id.add_office) {
			startActivity(new Intent(this, AddOffice.class));
		}

		return true;
	}
}
