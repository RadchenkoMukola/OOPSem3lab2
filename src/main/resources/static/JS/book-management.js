search()

function addBookInstance(id) {
    $.ajax({
        url: "/api/addBookInstance",
        data: {
            isbn: getBookIsbn(id),
            id: id
        },
        type: "post",
        async: false
    })
}

function getBookIsbn(id) {
    return document.getElementById(`book-isbn-input-field${id}`).value
}

function addBook() {
    $.ajax({
        url: "/api/addBook",
        data: {
            title: getBookTitle(),
            description: getBookDescription()
        },
        type: "post",
        async: false
    }).done(search)
}

function getBookTitle() {
    return document.getElementById("title-input-field").value
}

function getBookDescription() {
    return document.getElementById("description-input-field").value
}

function search() {
    $.ajax({
        url: "/api/findBookByPrompt",
        data: {
            prompt: getDesiredPrompt()
        },
        type: "get",
        async: false
    }).done(updateBooksList)
}

function getDesiredPrompt() {
    return document.getElementById("prompt-input-field").value
}

function updateBooksList(instances) {
    let div = document.getElementById("prompt-search-results")
    let html = `<table>
        <thead>
            <th>TITLE</th>
            <th>DESCRIPTION</th>
            <th>ISBN</th>
        </thead>
    <tbody>`

    instances.forEach(instance => html += getBookHTMLTableEntry(instance))

    html += "</tbody></table>"

    div.innerHTML = html
}

function getBookHTMLTableEntry(book) {
    return ` <tr xmlns="http://www.w3.org/1999/html">
            <td>${book.title}</td>
            <td>${book.description}</td>
            <td><input id="book-isbn-input-field${book.id}">
            <button onclick="addBookInstance(${book.id})">add book instance</button></td>
        </tr>
    `
}