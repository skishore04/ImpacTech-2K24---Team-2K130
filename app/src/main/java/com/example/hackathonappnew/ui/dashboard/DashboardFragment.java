package com.example.hackathonappnew.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hackathonappnew.OptionsAdapter;
import com.example.hackathonappnew.R;
import com.example.hackathonappnew.databinding.FragmentDashboardBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private Spinner placeSpinner, numberSpinner;
    private RecyclerView recyclerView;
    private ArrayList<String> optionsList, numbersList;
    private ArrayAdapter<String> placeAdapter, numberAdapter;
    private OptionsAdapter optionsAdapter;
    private DatabaseReference placesRef;
    private DashboardViewModel dashboardViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        placeSpinner = binding.spinerplace;
        numberSpinner = binding.numberspinner;
        recyclerView = binding.recyclerViewOptions;
        dashboardViewModel = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);

        optionsList = new ArrayList<>();
        numbersList = new ArrayList<>();

        // Populate numbers list from 1 to 10
        for (int i = 1; i <= 10; i++) {
            numbersList.add(String.valueOf(i));
        }
        Button nextButton = root.findViewById(R.id.subb);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the next fragment
                Toast.makeText(requireContext(), "Tasks Added Successfully", Toast.LENGTH_SHORT).show();
            }
        });
        placeAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, optionsList);
        placeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        placeSpinner.setAdapter(placeAdapter);

        numberAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, numbersList);
        numberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numberSpinner.setAdapter(numberAdapter);

        optionsAdapter = new OptionsAdapter(optionsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(optionsAdapter);

        placesRef = FirebaseDatabase.getInstance().getReference("places");

        placesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                optionsList.clear();
                optionsList.add("Select Your Option");
                for (DataSnapshot item : snapshot.getChildren()) {
                    optionsList.add(item.getKey());
                }
                placeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled event
            }
        });

        placeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedPlace = optionsList.get(position);
                if (!selectedPlace.equals("Select Your Option")) {
                    // Send selected data to ViewModel
                    dashboardViewModel.setSelectedPlace(selectedPlace);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle nothing selected
            }
        });

        numberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedNumber = numbersList.get(position);
                if (!selectedNumber.equals("Select Number")) {
                    // Handle selected number
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle nothing selected
            }
        });

        return root;
    }

    private void loadOptions(String place) {
        DatabaseReference optionsRef = placesRef.child(place);
        optionsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> options = new ArrayList<>();
                for (DataSnapshot optionSnapshot : snapshot.getChildren()) {
                    options.add(optionSnapshot.getKey());
                }
                optionsAdapter.setOptions(options);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled event
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
