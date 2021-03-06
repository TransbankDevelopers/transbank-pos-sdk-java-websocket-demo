package cl.transbank.pos.websocket;

import cl.transbank.pos.POS;
import cl.transbank.pos.exceptions.TransbankCannotOpenPortException;
import cl.transbank.pos.exceptions.TransbankInvalidPortException;
import cl.transbank.pos.exceptions.TransbankLinkException;
import cl.transbank.pos.exceptions.TransbankPortNotConfiguredException;
import cl.transbank.pos.responses.KeysResponse;
import cl.transbank.pos.responses.SaleResponse;

import cl.transbank.pos.utils.TransbankWrap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

public class POSService {

    private static final boolean fakeMode = true;

    private static final Logger logger = LogManager.getLogger(POSService.class);

    private static POS pos = null;

    static {
        try {
            pos = POS.getInstance();
        } catch (TransbankLinkException e) {
            e.printStackTrace();
            logger.warn("No pos!!!!");
            if (!fakeMode) {
                throw new RuntimeException("Error when initializing the POS");
            } else {
                logger.warn("Fake Mode On");
            }
        }
    }

    public static List<String> listPorts() throws TransbankLinkException {
        if (fakeMode) {
            return Arrays.asList("COM1",
                    "COM2",
                    "/usbmodem0123456789");
        }
        return pos.listPorts();
    }

    public static void closePort() {
        if (!fakeMode) {
            pos.closePort();
        }
    }

    public static KeysResponse loadKeys() throws TransbankPortNotConfiguredException {
        if (fakeMode) {
            return new KeysResponse(0, 0, 0, "ok");
        }
        return pos.loadKeys();
    }

    public static SaleResponse getLastSale() throws TransbankPortNotConfiguredException {
        if (fakeMode) {
            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, +7);
            Date date2 = cal.getTime();
            String dateString = new SimpleDateFormat("yyyy-MM-dd").format(date);
            String fromdate = new SimpleDateFormat("dd-MM-yyyy").format(date2);
            return new SaleResponse("0|0|0|0|0|0|0|0|0|0|0|0|"+date+"|0|0|"+date2+"|131415|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0");
        }
        return pos.getLastSale();
    }

    public static SaleResponse sale(int amount, int ticket) throws TransbankPortNotConfiguredException {
        if (fakeMode) {
            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, +7);
            Date date2 = cal.getTime();
            String dateString = new SimpleDateFormat("yyyy-MM-dd").format(date);
            String fromdate = new SimpleDateFormat("dd-MM-yyyy").format(date2);
            return new SaleResponse("0|0|0|0|0|0|0|0|0|0|0|0|"+date+"|0|0|"+date2+"|131415|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0");
        }
        return pos.sale(amount, ticket);
    }

    public static void openPort(String portname) throws TransbankCannotOpenPortException, TransbankInvalidPortException {
        if (fakeMode) {
            return;
        }
        pos.openPort(portname);
    }

    public static boolean poll() throws TransbankPortNotConfiguredException {
        if (fakeMode) {
            return true;
        }
        return pos.poll();
    }
}
