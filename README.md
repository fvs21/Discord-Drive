# Discord-Storage

Web application that allows users to upload files to a spring boot backend, that then, using the discord api, uploads it to a private discord server to store them.

As discord only allows an upload size limit of 25 mb, the backend splits the file into serveral 25mb chunks to succesfully upload them, also encrypting the chunks.
