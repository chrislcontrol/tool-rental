package tool.rental.domain.controllers;

import tool.rental.domain.dto.RentalReportDTO;
import tool.rental.domain.use_cases.GetRentalReportUseCase;
import tool.rental.utils.Controller;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.ToastError;

import java.util.List;

/**
 * The controller for generating a rental report.
 */
public class RentalReportController extends Controller {

    /**
     * The use case to get the rental report.
     */
    private final GetRentalReportUseCase getRentalReportUseCase = new GetRentalReportUseCase();

    /**
     * Creates a new instance of the RentalReportController.
     *
     * @param frame the presentation frame
     */
    public RentalReportController(PresentationFrame frame) {
        super(frame);
    }

    /**
     * Gets the rental report.
     *
     * @return a list of strings representing the rental report
     * @throws ToastError if an error occurs
     */
    public List<String[]> getRentalReport() throws ToastError {
        List<RentalReportDTO> reports = getRentalReportUseCase.execute();
        return reports.stream().map(
                report -> new String[] {
                        report.id(),
                        report.rentalDate(),
                        report.devolutionDate(),
                        report.friend(),
                        report.tool()
                }
        ).toList();
    }
}
