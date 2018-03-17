package com.semansoft.ezanisaat;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kemalettinsargin.mylib.MyFragmentActivity;
import com.kemalettinsargin.mylib.Util;
import com.semansoft.ezanisaat.adapter.LocationsAdapter;
import com.semansoft.ezanisaat.model.City;
import com.semansoft.ezanisaat.model.Country;
import com.semansoft.ezanisaat.model.TimesOfDay;
import com.semansoft.ezanisaat.model.Town;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Written by "كمال الدّين صارغين"  on 09.03.2018.
 * و من الله توفیق
 */
public class AddLocationsActivity extends MyFragmentActivity implements View.OnClickListener, SearchView.OnQueryTextListener {
    private List<Country> ulkeler;
    private List<City> sehirler;
    private List<Town> ilceler;
    private static final int ULKE=0,IL=1,ILCE=2;
    private int type=0;
    private RecyclerView recyclerView;
    private SearchView searchView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_locations);
        setSupportActionBar((android.support.v7.widget.Toolbar) findViewById(R.id.toolbar));
        recyclerView=(RecyclerView) getChild(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        getUlkeler();
    }


    public void setUlkeler(List<Country> ulkeler) {
        int pos;
        type=ULKE;
        Country Turkiye=ulkeler.get(pos=ulkeler.indexOf(new Country("2")));
        ulkeler.remove(pos);
        ulkeler.add(0,Turkiye);
        this.ulkeler = ulkeler;
        recyclerView.setAdapter(new LocationsAdapter(this,ulkeler));
    }

    public void setSehirler(List<City> sehirler) {
        this.sehirler = sehirler;
        type=IL;
        ((LocationsAdapter)recyclerView.getAdapter()).setItems(sehirler);
    }

    public void setIlceler(List<Town> ilceler) {
        this.ilceler = ilceler;
        type=ILCE;
        ((LocationsAdapter)recyclerView.getAdapter()).setItems(ilceler);
    }

    private void setSaatler(Town townNew){
        List<Town> towns;
        if(Util.hasPref(this,C.KEY_LOCATIONS))
            towns=getGson().fromJson(Util.getPref(this,C.KEY_LOCATIONS), new TypeToken<List<Town>>(){}.getType());
        else towns=new ArrayList<>();
        if(towns.contains(townNew)){
            Town townOld=towns.get(towns.indexOf(townNew));
            TimesOfDay first=townNew.getVakitler().get(0);
            int oldIndex=townOld.getVakitler().indexOf(first);
            townNew.getVakitler().get(0).setYesterDay(townOld.getVakitler().get(oldIndex-1));
            townNew.getVakitler().get(0).setToMorrow(townNew.getVakitler().get(1));
        }
        for (int i = 1; i < (townNew.getVakitler().size()-1); i++) {
            TimesOfDay tod=townNew.getVakitler().get(i);
            tod.setYesterDay(townNew.getVakitler().get(i-1));
            tod.setToMorrow(townNew.getVakitler().get(i+1));
        }
        towns.remove(townNew);
        towns.add(townNew);
        Util.savePref(this,C.KEY_LOCATIONS,getGson().toJson(towns));
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    public void getUlkeler() {
        showLoading();
        getApi().getCountries().enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                if(isDestroyed())return;
                dismissLoading();
                if(response.isSuccessful()&&response.body()!=null&&!response.body().isEmpty()){
                    setUlkeler(response.body());

                }else onFailure(null,null);
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                if(isDestroyed())return;
                dismissLoading();
                showErrorDialogRetry(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==DialogInterface.BUTTON_POSITIVE){
                            getUlkeler();
                        }else finish();
                    }
                });
            }
        });
    }
    public void getSehirler(final String code) {
        showLoading();
        getApi().getCities(code).enqueue(new Callback<List<City>>() {
            @Override
            public void onResponse(Call<List<City>> call, Response<List<City>> response) {
                if(isDestroyed())return;
                dismissLoading();
                if(response.isSuccessful()&&response.body()!=null&&!response.body().isEmpty()){
                    setSehirler(response.body());

                }else onFailure(null,null);
            }

            @Override
            public void onFailure(Call<List<City>> call, Throwable t) {
                if(isDestroyed())return;
                dismissLoading();
                showErrorDialogRetry(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==DialogInterface.BUTTON_POSITIVE){
                            getSehirler(code);
                        }else finish();
                    }
                });
            }
        });
    }
    public void getIlceler(final String code) {
        showLoading();
        getApi().getTowns(code).enqueue(new Callback<List<Town>>() {
            @Override
            public void onResponse(Call<List<Town>> call, Response<List<Town>> response) {
                if(isDestroyed())return;
                dismissLoading();
                if(response.isSuccessful()&&response.body()!=null&&!response.body().isEmpty()){
                    setIlceler(response.body());

                }else onFailure(null,null);
            }

            @Override
            public void onFailure(Call<List<Town>> call, Throwable t) {
                if(isDestroyed())return;
                dismissLoading();
                showErrorDialogRetry(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==DialogInterface.BUTTON_POSITIVE){
                            getIlceler(code);
                        }else finish();
                    }
                });
            }
        });
    }
    public void getSaatler(final Town town) {
        showLoading();
        getApi().getTimes(town.getIlceID()).enqueue(new Callback<List<TimesOfDay>>() {
            @Override
            public void onResponse(Call<List<TimesOfDay>> call, Response<List<TimesOfDay>> response) {
                if(isDestroyed())return;
                dismissLoading();
                if(response.isSuccessful()&&response.body()!=null&&!response.body().isEmpty()){
                    town.setVakitler(response.body());
                    setSaatler(town);
                }else onFailure(null,null);
            }

            @Override
            public void onFailure(Call<List<TimesOfDay>> call, Throwable t) {
                if(isDestroyed())return;
                dismissLoading();
                showErrorDialogRetry(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==DialogInterface.BUTTON_POSITIVE){
                            getSaatler(town);
                        }else finish();
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        Object obj=v.getTag();
        if(obj instanceof Country){
            getSehirler(((Country) obj).getUlkeID());
        }else if(obj instanceof City){
            getIlceler(((City) obj).getSehirID());
        }else if(obj instanceof Town){
            getSaatler((Town) obj);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_location, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        List<Object> array=new ArrayList<>();
        switch (type){
            case ULKE:
                for (Country country : ulkeler) {
                    if(country.getUlkeAdi().toLowerCase().contains(newText.toLowerCase()))array.add(country);
                }
                break;
            case IL:
                for (City country : sehirler) {
                    if(country.getSehirAdi().toLowerCase().contains(newText.toLowerCase()))array.add(country);
                }
                break;
            case ILCE:
                for (Town country : ilceler) {
                    if(country.getIlceAdi().toLowerCase().contains(newText.toLowerCase()))array.add(country);
                }
                break;
        }
        LocationsAdapter adapter=(LocationsAdapter)recyclerView.getAdapter();
        adapter.setItems(array);
        return false;
    }
}
