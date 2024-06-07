package tool.rental.domain.controllers;

import tool.rental.domain.dao.FriendRentalSummary;
import tool.rental.domain.use_cases.GetRentalSummaryUseCase;
import tool.rental.utils.Controller;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.ToastError;

import java.util.ArrayList;
import java.util.List;

/**
 * The controller for the friends rank.
 */
public class FriendsRankController extends Controller {

    /**
     * The use case to get the rental summary.
     */
    private final GetRentalSummaryUseCase getRentalSummaryUseCase = new GetRentalSummaryUseCase();

    /**
     * Creates a new instance of the FriendsRankController.
     *
     * @param frame the presentation frame
     */
    public FriendsRankController(PresentationFrame frame) {
        super(frame);
    }

    /**
     * Gets the rental summary.
     *
     * @return the rental summary as a list of table rows
     * @throws ToastError if an error occurs
     */
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
