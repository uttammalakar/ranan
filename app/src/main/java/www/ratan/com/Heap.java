package www.ratan.com;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Edges<T>{

    Vertex<T> vertex1,vertex2;
    long weight;
    boolean isDirect=false;

    public Edges(Vertex<T> vertex1, Vertex<T> vertex2, long weight, boolean isDirect) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.weight = weight;
        this.isDirect = isDirect;
    }

    public Edges(Vertex<T> vertex1, Vertex<T> vertex2, long weight) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.weight = weight;
    }

    public Edges(Vertex<T> vertex1, Vertex<T> vertex2, boolean isDirect) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.isDirect = isDirect;
    }

    public Vertex<T> getVertex1() {
        return vertex1;
    }

    public void setVertex1(Vertex<T> vertex1) {
        this.vertex1 = vertex1;
    }

    public Vertex<T> getVertex2() {
        return vertex2;
    }

    public void setVertex2(Vertex<T> vertex2) {
        this.vertex2 = vertex2;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public boolean isDirect() {
        return isDirect;
    }

    public void setDirect(boolean direct) {
        isDirect = direct;
    }

    @Override
    public String toString() {
        return "Edges{" +
                "vertex1=" + vertex1.toString() +
                ", vertex2=" + vertex2.toString() +
                ", weight=" + weight +
                ", isDirect=" + isDirect +
                '}';
    }
}

class Vertex<T>{

T id;

List<Edges<T>> allEdges=new ArrayList<>();
List< Vertex<T> > adjacencyVertex=new ArrayList<>();

    public Vertex(T id) {
        this.id = id;
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public List<Edges<T>> getAllEdges() {
        return allEdges;
    }

    public void setAllEdges(List<Edges<T>> allEdges) {
        this.allEdges = allEdges;
    }

    public List<Vertex<T>> getAdjacencyVertex() {
        return adjacencyVertex;
    }

    public void setAdjacencyVertex(List<Vertex<T>> adjacencyVertex) {
        this.adjacencyVertex = adjacencyVertex;
    }

    public void addAdjacencyVertex(Vertex<T> v,Edges<T> e){

        allEdges.add(e);
        adjacencyVertex.add(v);

    }

    public String toString(){

        return String.valueOf(id);

    }

}

class Graph<T>{

    List<Edges<T>> allEdges=new ArrayList<>();
    Map<T,Vertex<T>> allVertex=new HashMap<>();

    boolean isDirect=false;

    public Graph(boolean isDirect) {
        this.isDirect = isDirect;
    }

    public List<Edges<T>> getAllEdges() {
        return allEdges;
    }

    public void setAllEdges(List<Edges<T>> allEdges) {
        this.allEdges = allEdges;
    }

    public Collection< Vertex<T>> getMap() {
        return allVertex.values();
    }

    public void setMap(Map<T, Vertex<T>> map) {
        this.allVertex = map;
    }

    public boolean isDirect() {
        return isDirect;
    }

    public void setDirect(boolean direct) {
        isDirect = direct;
    }

    public void addEdges(T id1,T id2,long weight){

        Vertex<T> vertex1=null;

        if( allVertex.containsKey(id1) ){

            vertex1=allVertex.get(id1);

        }else{

            vertex1=new Vertex<>(id1);
            allVertex.put(id1,vertex1);

        }

        Vertex<T> vertex2=null;

        if(allVertex.containsKey(id2)){

            vertex2=allVertex.get(id2);

        }else{

            vertex2=new Vertex<>(id2);

            allVertex.put(id2,vertex2);

        }

        Edges<T> edges=new Edges<>(vertex1,vertex2,weight);

allEdges.add(edges);

vertex1.addAdjacencyVertex(vertex2,edges);

if(!isDirect){

    vertex2.addAdjacencyVertex(vertex1,edges);

}

    }

    public void addEdges(T id1,T id2){

        addEdges(id1,id2,0);

    }

    public Vertex<T> addSingleVertex(T id){

        if(allVertex.containsKey(id)){

            return allVertex.get(id);

        }

Vertex<T> v=new Vertex<>(id);

allVertex.put(id,v);

  return v;

    }

@RequiresApi(api = Build.VERSION_CODES.N)
public void addVertex(Vertex<T> vertex){

        if(allVertex.containsKey(vertex.getId())){

            return;

        }

        allVertex.put(vertex.getId(),vertex);

        vertex.getAllEdges().forEach( (e)->{

            allEdges.add(e);

        } );

}

public Vertex<T> getVertex(T key){

        return allVertex.get(key);

}

    @Override
    public String toString() {
        return "Graph{" +
                "allEdges=" + allEdges +
                ", allVertex=" + allVertex +
                ", isDirect=" + isDirect +
                '}';
    }

}

public class Heap<T> {

    List<Node> allNodes=new ArrayList<>();
    Map<T,Integer> nodePosition=new HashMap<>();

 class Node<T>{

    T id;
    long weight;

    public Node(T id, long weight) {
        this.id = id;
        this.weight = weight;
    }

    public Node() {

    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", weight=" + weight +
                '}';
    }

}

public Node<T> extractMinNode(){

     Node<T> minNode=new Node<>((T)allNodes.get(0).getId(),allNodes.get(0).getWeight());

     int size=allNodes.size()-1; long currentWeight=allNodes.get(size).getWeight();

     allNodes.get(0).setWeight(currentWeight);
allNodes.get(0).setId( allNodes.get(size).getId() );

nodePosition.remove(allNodes.get(0));

nodePosition.remove(minNode.getId()) ;

nodePosition.put((T)allNodes.get(0).getId(),0);

allNodes.remove(size);

--size;

int current=0;

while(true){

int left=2*current+1,right=2*current+2;

if(left>size){

    break;

}

if(right>size){

    right=left;

}

int smaller=allNodes.get(left).getWeight()<=allNodes.get(right).getWeight()? left:right;

if(allNodes.get(current).getWeight()>allNodes.get(smaller).getWeight()){

    swap(allNodes.get(current),allNodes.get(smaller));

    setMappPosition((T)allNodes.get(current).getId(),(T)allNodes.get(smaller).getId(),current,smaller);

    current=smaller;

}else{

    break;

}

}

     return minNode;

}

public T extractMin(){

     Node<T> node=extractMinNode();

return  node.getId();

}

public void decrease(T id,long weight){

     Integer position=nodePosition.get(id);

     allNodes.get(position).setWeight(weight);

     int current=allNodes.size()-1,parent=(current-1)/2;

     while(parent>=0){

         if(allNodes.get(parent).getWeight()>allNodes.get(current).getWeight()){

             swap(allNodes.get(parent),allNodes.get(current));

             setMappPosition((T)allNodes.get(parent).getId(),(T)allNodes.get(current).getId(),parent,current);

             current=parent;
             parent=(parent-1)/2;

         }else{

             break;

         }

     }

}

public void push(T id,long weight){

    Node<T> node=new Node<>(id,weight);

allNodes.add(node);

int size=allNodes.size(),current=size-1,parent=(current-1)/2;

while(parent>=0){

    if(allNodes.get(parent).getWeight()>allNodes.get(current).getWeight()){

swap(allNodes.get(parent),allNodes.get(current));

setMappPosition((T)allNodes.get(parent).getId(),(T)allNodes.get(current).getId(),parent,current);

current=parent;
parent=(parent-1)/2;

    }else{

        break;

    }

}

}

public void print(){

     for(Node<T> node: allNodes){

         System.out.println(node.getId()+"      "+node.getWeight());

     }

}

    public void printMap(){

        System.out.println(nodePosition);

    }

public Long getWeight(T id){

     Integer position=nodePosition.get(id);

     if(position==null){

         return null;

     }

     return allNodes.get(position).getWeight();

}

public Node<T> getVertex(T id){

     Integer position=nodePosition.get(id);

     return allNodes.get(position);

}

public boolean isEmpty(){

     return allNodes.isEmpty();

}

public void swap(Node<T> node1,Node<T>node2){

    T id=node1.getId();
    long weight=node1.getWeight();

    node1.setWeight(node2.getWeight());
    node1.setId(node2.getId());

    node2.setId(id);
    node2.setWeight(weight);

}

public void setMappPosition(T id1, T id2, int pos1, int pos2){

    nodePosition.remove(id1);
    nodePosition.remove(id2);

    nodePosition.put(id1,pos1);
    nodePosition.put(id2,pos2);

}

}
