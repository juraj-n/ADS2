package zadanie2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SvkNodeReader {
    // slovakia_nodes = id;lat;lon
    // testovacie_oblasti = id;min_lat;min_lon;max_lat;max_lon


    /**
     * Kód vytvorený pomocou AI, zdokumentované v kapitole 2.
     * */
    public static List<SvkNode> loadSvkNodes(String path) {
        List<SvkNode> svkNodes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            br.readLine(); // Skips header
            while ((line = br.readLine()) != null) {
                int first = line.indexOf(";");
                int second = line.indexOf(";", first + 1);

                String id = line.substring(0, first);
                double lat = Double.parseDouble(line.substring(first + 1, second));
                double lon = Double.parseDouble(line.substring(second + 1));

                svkNodes.add(new SvkNode(id, lat, lon));
            }
        } catch (IOException ioex) {
            System.out.println(ioex.getMessage());
        }

        return svkNodes;
    }
    public static List<SvkNodesArea> loadSvkNodesAreas(String path) {
        List<SvkNodesArea> svkNodesAreas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                int first = line.indexOf(";");
                int second = line.indexOf(";", first + 1);
                int third = line.indexOf(";", second + 1);
                int fourth = line.indexOf(";", third + 1);

                String id = line.substring(0, first);
                double minLat = Double.parseDouble(line.substring(first + 1, second));
                double minLon = Double.parseDouble(line.substring(second + 1, third));
                double maxLat = Double.parseDouble(line.substring(third + 1, fourth));
                double maxLon = Double.parseDouble(line.substring(fourth + 1));

                svkNodesAreas.add(new SvkNodesArea(id, minLat, maxLat, minLon, maxLon));
            }
        } catch (IOException ioex) {
            System.out.println("Exception caught: " + ioex.getMessage());
        }

        return svkNodesAreas;
    }
}
