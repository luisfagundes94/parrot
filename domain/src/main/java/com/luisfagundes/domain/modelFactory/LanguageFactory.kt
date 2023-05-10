package com.luisfagundes.domain.modelFactory

import com.luisfagundes.domain.models.Language

object LanguageFactory {
    val languages = listOf(
        Language(
            id = "d4d5d87e-c0a1-4f35-bb81-5f2709e628aa",
            name = "English",
            nativeName = "English",
            code = "en"
        ),
        Language(
            id = "06305ea0-5ab9-4b2d-bebc-c99e93bc5d59",
            name = "Portuguese",
            nativeName = "Português",
            code = "pt"
        ),
    )

    val languagesInJsonString =  """
           [
                {
                "id": "d4d5d87e-c0a1-4f35-bb81-5f2709e628aa",
                "name": "English",
                "nativeName": "English",
                "code": "en"
              },
                {
                "id": "06305ea0-5ab9-4b2d-bebc-c99e93bc5d59",
                "name": "Portuguese",
                "nativeName": "Português",
                "code": "pt"
              }
           ]
            """
}