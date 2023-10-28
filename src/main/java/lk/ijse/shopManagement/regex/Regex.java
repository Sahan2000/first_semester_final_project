package lk.ijse.shopManagement.regex;

import java.util.regex.Pattern;

public class Regex {
    private static Regex regex;
    private final Pattern namePattern;
    private final Pattern customerIdPattern;
    private final Pattern cityPattern;
    private final Pattern oldIDPattern;
    private final Pattern intPattern;
    private final Pattern employeeId;
    private final Pattern idPattern;
    private final Pattern contactPattern;
    private final Pattern salaryPattern;
    private final Pattern supplierIdPattern;
    private final Pattern itemIdPattern;
    private  Regex(){
        namePattern = Pattern.compile("^[a-zA-Z '.-]{4,}$");
        customerIdPattern = Pattern.compile("C(00[1-9]|0[1-9][0-9]|[1-9][0-9]{2})");
        cityPattern = Pattern.compile("[a-zA-Z]{4,}$");
        oldIDPattern = Pattern.compile("^[0-9]{9}[vVxX]$");
        intPattern = Pattern.compile("^[1-9][0-9]?$|^100$");
        employeeId = Pattern.compile("^E\\d{3}$");
        idPattern = Pattern.compile("^([0-9]{9}[x|X|v|V]|[0-9]{12})$");
        contactPattern = Pattern.compile("^(?:0|94|\\+94|0094)?(?:(11|21|23|24|25|26|27|31|32|33|34|35|36|37|38|41|45|47|51|52|54|55|57|63|65|66|67|81|91)(0|2|3|4|5|7|9)|7(0|1|2|4|5|6|7|8)\\d)\\d{6}$");
        salaryPattern = Pattern.compile("^[0-9]+\\.?[0-9]*$");
        supplierIdPattern = Pattern.compile("S(00[1-9]|0[1-9][0-9]|[1-9][0-9]{2})");
        itemIdPattern = Pattern.compile("I(00[1-9]|0[1-9][0-9]|[1-9][0-9]{2})");
    }
    public static Regex getInstance(){

        if (regex==null){
            return regex= new Regex();
        }
        return regex;
    }
    public  Pattern getPattern(RegexPattern type){
        switch (type){
            case CUSTOMER_ID_PATTERN:ID_PATTERN:
                return Pattern.compile(String.valueOf(customerIdPattern));
            case EMPLOYEE_ID_PATTERN:
                return Pattern.compile(String.valueOf(employeeId));
            case NAME_PATTERN:
                return Pattern.compile(String.valueOf(namePattern));
            case CONTACT_PATTERN:
                return Pattern.compile(String.valueOf(contactPattern));
            case CUSTOMER_NIC_OLD_PATTERN:
                return Pattern.compile(String.valueOf(oldIDPattern));
            case CUSTOMER_NIC_NEW_PTTERN:
                return Pattern.compile(String.valueOf(idPattern));
            case DOUBLE_PATTERN:
                return Pattern.compile(String.valueOf(salaryPattern));
            case ADDRESS_PATTERN:
                return Pattern.compile(String.valueOf(cityPattern));
            case SUPPLIER_ID_PATTERN:
                return Pattern.compile(String.valueOf(supplierIdPattern));
            case INT_PATTERN:
                return Pattern.compile(String.valueOf(intPattern));
            case ITEM_ID_PATTERN:
                return Pattern.compile(String.valueOf(itemIdPattern));
            default:
                throw new RuntimeException("Pattern not found");
        }
    }
}
