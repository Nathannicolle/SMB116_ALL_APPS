package fr.caensup.lsts.smb116.listcontact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    private ContactHelper contactHelper;
    private TextView tvContact;
    private EditText etContent;
    private Button btnGet;
    private ContactProvider cp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvContact = findViewById(R.id.tvContacts);
        etContent = findViewById(R.id.etContentURI);
        btnGet = findViewById(R.id.btnGet);

        initProvider();
        prepareComponents();

        tvContact.setText(getContactAsString());
        initDb();
        createContact();
    }

    private String getContactAsString() {
        SQLiteDatabase db = contactHelper.getReadableDatabase();
        Cursor cursor = db.query(ContactHelper.TABLE_CONTACT, ContactHelper.ALL, null, null, null, null, null);
        StringBuilder contacts = new StringBuilder();
        contacts.append("Contacts number : ");
        contacts.append(cursor.getCount());
        contacts.append("\n");
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String prenom = cursor.getString(1);
            String nom = cursor.getString(2);
            String tel = cursor.getString(3);
            contacts.append(id);
            contacts.append(" ");
            contacts.append(prenom);
            contacts.append(" ");
            contacts.append(tel);
        }

        return contacts.toString();
    }

    private void initDb() {
        contactHelper = new ContactHelper(this);
    }

    private void initProvider() {
        cp = new ContactProvider();
    }

    private void createContact() {
        SQLiteDatabase db = contactHelper.getWritableDatabase();

        db.beginTransaction();
        try {
            List<String> contacts = getData();
            for (String contact : contacts) {
                StringTokenizer st = new StringTokenizer(contact, ",");
                ContentResolver cr = getContentResolver();
                ContentValues values=new ContentValues();
                values.put(ContactHelper.NOM_COLONNE_PRENOM,st.nextToken());
                values.put(ContactHelper.NOM_COLONNE_NOM, st.nextToken());
                values.put(ContactHelper.NOM_COLONNE_NUMERO_TEL, st.nextToken());
                db.insert(ContactHelper.TABLE_CONTACT, null, values);
            }
            db.setTransactionSuccessful();
        }
        catch (SQLiteException e) {
        }
        finally {
            db.endTransaction();
            db.close();
        }

    }

    private List<String> getData() {
        return Arrays.asList(new String[]{
                "Richart,Moss,06 52 72 02 32",
                "Dorothy,Stennett,06 02 62 52 22",
                "Benedicta,Owain,06 92 02 42 82",
                "Skip,Heyfield,06 02 22 72 22",
                "Shea,Nolte,06 92 72 82 82",
                "Diannne,Clandillon,06 92 92 82 32",
                "Kippy,Jillis,06 82 32 32 62",
                "Gracie,Braams,06 22 82 22 62",
                "Connie,Corless,06 02 92 22 82",
                "Arlena,Gergolet,06 42 02 22 02",
                "Adele,Cattell,06 02 42 02 12",
                "Dew,Wakes,06 62 32 82 02",
                "Jane,Whorlow,06 12 72 82 92",
                "Benedikt,Siddens,06 32 92 92 22",
                "Odessa,Scarlet,06 72 02 02 52",
                "Sarine,Scantleberry,06 22 22 62 32",
                "Marco,Meier,06 62 92 92 02",
                "Aldric,Caghy,06 82 12 32 32",
                "Elsa,Andres,06 92 32 52 72",
                "Carlina,Denisot,06 02 62 32 22",
                "Cheslie,Seeviour,06 02 82 52 52",
                "Clayson,Jago,06 32 02 42 12",
                "Jobi,Woolvett,06 02 62 12 12",
                "Dorothy,Danielkiewicz,06 12 92 62 62",
                "Paulita,Downes,06 92 92 82 42",
                "Nanice,Baalham,06 12 42 72 92",
                "Barbabra,Speck,06 42 82 22 82",
                "Angelique,Spikings,06 82 92 82 62",
                "Jo-ann,Rachuig,06 32 32 42 22",
                "Cecilla,Lomen,06 82 92 12 52",
                "Alvy,Blasoni,06 42 82 72 82",
                "Elva,Clowley,06 82 12 32 82",
                "Nicolai,McCunn,06 92 02 32 42",
                "Ruthann,Struss,06 62 32 82 02",
                "Angus,Laimable,06 02 22 92 02",
                "Miran,Ewing,06 12 02 92 92",
                "Karna,Skain,06 52 32 12 32",
                "Carlynne,Vasyunkin,06 62 32 82 32",
                "Brett,Loughnan,06 72 12 72 22",
                "Free,Pechard,06 62 82 72 22",
                "Lucais,Ryburn,06 52 72 52 92",
                "Emmi,Gerber,06 42 32 02 22",
                "Annora,Yallop,06 52 62 82 72",
                "Maddy,Bedbrough,06 22 62 22 92",
                "Chev,Vasyaev,06 42 22 92 02",
                "Ashlee,Motte,06 62 32 72 62",
                "Worth,Lynam,06 72 12 62 12",
                "Charles,Drakard,06 22 82 42 82",
                "Wood,Walcher,06 12 62 42 42",
                "Sandy,Iacobo,06 82 32 82 62"
        });
    }

    private void prepareComponents() {
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = etContent.getText().toString();
                URI uriContent = null;
                String content = "";
                try {
                    uriContent = Uri.parse(uri);
                    content= getURIContent(uriContent);
                } catch(URISyntaxException e) {
                    e.printStackTrace();
                }

                tvContact.setText(content);
            }
        });
    }

    private String getURIContent(URI uri) {
        StringBuilder contacts = new StringBuilder();
        Cursor cursor = cp.query(uri, ContactHelper.ALL, null, null, null, null);
        StringBuilder txtContent = new StringBuilder();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String prenom = cursor.getString(1);
            String nom = cursor.getString(2);
            String tel = cursor.getString(3);
            contacts.append(id);
            contacts.append(" ");
            contacts.append(prenom);
            contacts.append(" ");
            contacts.append(tel);
        }

    }
}