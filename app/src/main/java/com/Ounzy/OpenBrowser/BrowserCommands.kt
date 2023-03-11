package com.Ounzy.OpenBrowser

interface BrowserCommands {
    fun refresh()

    fun loadUrl(url: String)

    fun goHome()

    fun setSelectedTab(index: Int)
}
