package com.freecode.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrieNode {
    //每个节点上存储一个字
	public char key=(char)0;
	//如果这个字在词的末尾，则bound=true
	public boolean bound=false;
	//当bound=true时，suggestion中存储匹配上的提示列表
	public List<Integer> suggestion= new ArrayList<>();
	//孩子节点。Character占两个字节，而Integer要占4个字节。
	public Map<Character,TrieNode> children= new HashMap<>();
	
	public TrieNode(){
	}
	
	public TrieNode(char k){
		this.key=k;
	}
}