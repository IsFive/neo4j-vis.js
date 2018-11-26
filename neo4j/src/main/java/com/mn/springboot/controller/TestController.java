package com.mn.springboot.controller;

import com.mn.springboot.utils.Neo4jUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
public class TestController {
    @Autowired
    private Neo4jUtil neo4jUtil;

    @GetMapping("get")
    public Map<String, Object> get(){
        Map<String, Object> retMap = new HashMap<>();
        //cql语句
        String cql = "match (m:Person{name: \"Meg Ryan\"}) return m";
        Set<Map<String ,Object>> nodeList = new HashSet<>();
        neo4jUtil.getList(cql,nodeList);
        retMap.put("nodeList",nodeList);
        return retMap;
    }

    @GetMapping("getPath")
    public Map<String, Object> getPath(String id){
        Map<String, Object> retMap = new HashMap<>();
        //cql语句  ID()可以获取节点自动生成的id
        String cql = "match l=(m)-[]-(n) where ID(m)="+id+" return l";
        //待返回的值，与cql return后的值顺序对应
        Set<Map<String ,Object>> nodeList = new HashSet<>();
        Set<Map<String ,Object>> edgeList = new HashSet<>();
        neo4jUtil.getPathList(cql,nodeList,edgeList);
        retMap.put("nodeList",nodeList);
        retMap.put("edgeList",edgeList);
        return retMap;
    }

    @GetMapping("getShortPath")
    public Map<String, Object> getShortPath(){
        Map<String, Object> retMap = new HashMap<>();
        //cql语句
        String cql = "match l=shortestPath(({name:'Keanu Reeves'})-[*]-({title:\"Jerry Maguire\"})) return l";
        //待返回的值，与cql return后的值顺序对应
        Set<Map<String ,Object>> nodeList = new HashSet<>();
        Set<Map<String ,Object>> edgeList = new HashSet<>();
        neo4jUtil.getPathList(cql,nodeList,edgeList);
        retMap.put("nodeList",nodeList);
        retMap.put("edgeList",edgeList);
        return retMap;
    }

    @GetMapping("getFields")
    public Map<String, Object> getFields(){
        Map<String, Object> retMap = new HashMap<>();
        //cql语句
        //String cql = "match (n:Person{name:\"Anthony Edwards\"}) return n.name as name,n.born as born";
        String cql = "match (n:Person) return count(n) as cou";
        retMap.put("fieldList",neo4jUtil.getFields(cql));
        return retMap;
    }

    @GetMapping("add")
    public void add(){
        //创建单个节点
        //String cql = "create (:Person{name:\"康康\"})";
        //创建多个节点
        //String cql = "create (:Person{name:\"李雷\"}) create (:Person{name:\"小明\"})";
        //根据已有节点创建关系
        //String cql = "match (n:Person{name:\"李雷\"}),(m:Person{name:\"小明\"}) create (n)-[r:friendRelation]->(m)";
        //同时创建节点和关系
        String cql = "create (:Person{name:\"张三\"})-[r:friendRelation]->(:Person{name:\"王五\"})";
        neo4jUtil.add(cql);
    }
}
