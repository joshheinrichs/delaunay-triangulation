//package modelAdapters;
//
//import javafx.scene.Group;
//import tools.AddVertexTool;
//import tools.MoveVertexTool;
//import tools.RemoveVertexTool;
//
///**
// * Created by joshheinrichs on 15-05-07.
// */
//public class CompleteGraphAdapter extends ModelAdapter {
//
//    public CompleteGraphAdapter() {
//        tools.add(new AddVertexTool(this));
//        tools.add(new MoveVertexTool(this));
//        tools.add(new RemoveVertexTool(this));
//    }
//
//    @Override
//    public String getName() {
//        return "Complete Graph";
//    }
//
//    @Override
//    public void addVertex(double x, double y) {
//
//    }
//
//    @Override
//    public void removeVertex(double x, double y) {
//
//    }
//
//    @Override
//    public void moveVertex(double fromX, double fromY, double toX, double toY) {
//
//    }
//
//    @Override
//    public Group draw() {
//        return null;
//    }
//
//    @Override
//    public Group dragDraw() {
//        return null;
//    }
//}
