package com.spacewl.adapter

interface AdapterDelegatesFactory {
    fun createDelegate(clazz: Class<ListItem>): AdapterDelegate
}