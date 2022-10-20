package com.Doctoor.app.model

import com.Doctoor.app.model.response.BaseModel

class Pageable<BM: BaseModel> {
    var first: Int = 0
    var next: Int = 0
    var prev: Int = 0
    var last: Int = 0
    var totalCount: Int = 0
    var incompleteResults: Boolean = false
    var items: MutableList<BM> = ArrayList()
}