package rznw.map.generator;

import rznw.map.Map;
import rznw.map.element.Path;

public class PathRenderer
{
    public void renderPathOnMap(Map map, MapPath path)
    {
        MapPoint[] points = path.getPoints();

        for (int i = 0; i < points.length; i++)
        {
            MapPoint point = points[i];
            map.setElement(point.getY(), point.getX(), new Path(point.getY(), point.getX()));
        }
    }
}
