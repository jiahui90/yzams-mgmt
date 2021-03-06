/*
 * AgentListener.java
 * 
 * Copyright(c) 2007-2012 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2012-09-17 17:48:48
 */
package com.yz.ams.client.agent;

/**
 * 用于Agent的ListenerList
 * @author Qiu Dongyue
 */
public class AgentEventListenerList {
    private final static AgentListener[] NULL_ARRAY = new AgentListener[0];
    private AgentListener[] listenerList = NULL_ARRAY;

    public AgentListener[] getListeners() {
        return listenerList;
    }

    public int getSize() {
        return listenerList.length;
    }

    public synchronized void add(AgentListener lis) {
        if (lis == null) {
            return;
        }
        if (listenerList == NULL_ARRAY) {
            listenerList = new AgentListener[]{lis};
        } else {
            int i = listenerList.length;
            AgentListener[] tmp = new AgentListener[i + 1];
            System.arraycopy(listenerList, 0, tmp, 0, i);
            tmp[i] = lis;
            listenerList = tmp;
        }
    }

    public synchronized void remove(AgentListener lis) {
        if (lis == null) {
            return;
        }
        // Is l on the list?
        int index = -1;
        for (int i = listenerList.length - 1; i >= 0; i--) {
            if (listenerList[i].equals(lis)) {
                index = i;
                break;
            }
        }

        // If so,  remove it
        if (index != -1) {
            AgentListener[] tmp = new AgentListener[listenerList.length - 1];
            System.arraycopy(listenerList, 0, tmp, 0, index);
            if (index < tmp.length) {
                System.arraycopy(listenerList, index + 1, tmp, index, tmp.length
                        - index);
            }
            listenerList = (tmp.length == 0) ? NULL_ARRAY : tmp;
        }
    }

    public synchronized void clear(){
        listenerList = NULL_ARRAY;
    }
}
