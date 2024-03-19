// NotificationsFragment.java
package com.example.hackathonappnew.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.hackathonappnew.HomeActivity;
import com.example.hackathonappnew.R;
import com.example.hackathonappnew.Voucher;
import com.example.hackathonappnew.databinding.FragmentNotificationsBinding;
import com.example.hackathonappnew.ui.dashboard.DashboardViewModel;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        DashboardViewModel dashboardViewModel = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);

        dashboardViewModel.getSelectedPlace().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String selectedPlace) {
                TextView textView = root.findViewById(R.id.textviewplace);
                textView.setText(selectedPlace);
            }
        });
        Button nextActivityButton = root.findViewById(R.id.voucher);
        nextActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the next activity here
                startActivity(new Intent(getActivity(), Voucher.class));
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
