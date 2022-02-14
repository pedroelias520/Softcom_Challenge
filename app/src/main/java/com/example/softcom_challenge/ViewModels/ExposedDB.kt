package com.example.softcom_challenge.ViewModels
import com.example.softcom_challenge.Models.Product
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class ExposedDB {
    object Category: Table(){
        val title =  varchar("title",20)
        val productsIds = (integer("productIds") references Product.id).nullable()
    }
    object Product: Table(){



        val id = varchar("id",12)
        val name = varchar("name",50)
        val price = double("price")
        val image = integer("image")
        val oldPrice = double("oldPrice")
        val description = varchar("description",300)
        override val primaryKey = PrimaryKey(id, name = "PK_Product_ID")
    }
    object Request:Table(){
        val id = integer("id").autoIncrement()
        val productIds = (integer("productIds").references(Product.id).nullable()
        val totalPrice = double("totalPrice")
        val observation  = varchar("observation", 300)
        val qtd = integer("qtd")
        val date = varchar("date",50)
    }

    init {
        Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
        transaction {
            Product.insert {
                it[id] = "1001 0000 0000"
                it[name] = "Ração bom dog"
                it[price] = 39.90
            }
        }

    }
}


