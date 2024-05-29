package tool.rental.domain.controllers;

import tool.rental.domain.dto.RentalReportDTO;
import tool.rental.domain.use_cases.GetRentalReportUseCase;
import tool.rental.utils.Controller;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.ToastError;

import java.util.ArrayList;
import java.util.List;

public class RentalReportController extends Controller {
    private final GetRentalReportUseCase getRentalReportUseCase = new GetRentalReportUseCase();

    public RentalReportController(PresentationFrame frame) {
        super(frame);
    }

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
