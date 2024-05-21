package tool.rental.domain.controllers;

import tool.rental.domain.dao.FriendRentalSummary;
import tool.rental.domain.use_cases.GetRentalSummaryUseCase;
import tool.rental.utils.Controller;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.ToastError;

import java.util.ArrayList;
import java.util.List;

public class FriendsRankController extends Controller {
    private final GetRentalSummaryUseCase getRentalSummaryUseCase = new GetRentalSummaryUseCase();

    public FriendsRankController(PresentationFrame frame) {
        super(frame);
    }

    public List<String[]> getRentalSummary() throws ToastError {
        List<FriendRentalSummary> result = getRentalSummaryUseCase.execute();
        ArrayList<String[]> summaries = new ArrayList<>(result.size());

        int idx = 1;

        for (FriendRentalSummary summary : result) {
            String[] row = {
                    String.valueOf(idx++),
                    summary.name(),
                    summary.socialSecurity(),
                    String.valueOf(summary.currentBorrowed()),
                    String.valueOf(summary.totalRental())
            };
            summaries.add(row);
        }

        return summaries;
    }
}
