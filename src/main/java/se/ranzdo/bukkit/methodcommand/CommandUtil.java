package se.ranzdo.bukkit.methodcommand;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandUtil {
    private static Pattern verifyArgumentsPattern = Pattern.compile("^(.*?)\\[(.*?)\\]$");

    public static String escapeArgumentVariable(String var) {
        if (var == null) {
            return null;
        }

        if (var.matches("^\\\\*\\?.*$")) {
            return "\\" + var;
        }

        return var;
    }

    public static Map<String, String[]> parseVerifiers(String verifiers) {
        Map<String, String[]> map = Maps.newLinkedHashMap();

        if (verifiers.equals("")) {
            return map;
        }

        String[] arguments = verifiers.split("\\|");

        for (String arg : arguments) {
            Matcher matcher = verifyArgumentsPattern.matcher(arg);
            if (!matcher.matches()) {
                throw new IllegalArgumentException("The argrument \"" + arg + "\" is in invalid form.");
            }

            List<String> parameters = Lists.newArrayList();

            String sparameters = matcher.group(2);
            if (sparameters != null) {
                for (String parameter : sparameters.split(",")) {
                    parameters.add(parameter.trim());
                }
            }

            String argName = matcher.group(1).trim();

            map.put(argName, parameters.toArray(new String[0]));
        }

        return map;
    }
}
