search()

let selectedInstance = null

function search() {
    $.ajax({
        url: "/api/findBookInstancesByPrompt",
        data: {
            prompt: getDesiredPrompt()
        },
        type: "get",
        async: false
    }).done(updateBooksInstancesList)
}

function getDesiredPrompt() {
    return document.getElementById("prompt-input-field").value
}

function getBorrowType() {
    return document.getElementById("borrower-borrow-type").value
}

function getDesiredBorrowerUsername() {
    return document.getElementById("borrower-username-input").value
}

function updateBooksInstancesList(instances) {
    let div = document.getElementById("prompt-search-results")
    let html = `<table>
        <thead>
            <th>ID</th>
            <th>ISBN</th>
            <th>TITLE</th>
            <th>DESCRIPTION</th>
            <th>STATUS</th>
        </thead>
    <tbody>`

    instances.forEach(instance => html += getBookInstanceHTMLTableEntry(instance))

    html += "</tbody></table>"

    div.innerHTML = html
}

function getBookInstanceHTMLTableEntry(instance) {
    return ` <tr xmlns="http://www.w3.org/1999/html">
            <td>${instance.book.id}</td>
            <td>${instance.isbn}</td>
            <td>${instance.book.title}</td>
            <td>${instance.book.description}</td>
            <td>${instance.status.match(/([A-Z]?[^A-Z]*)/g).slice(0,-1).join(" ")}</td>
            <td><button onclick="selectBookInstance(${instance.isbn})">select</button></td>
        </tr>
    `
}

function selectBookInstance(isbn) {
    $.ajax({
        url: "/api/findBookInstanceByISBN",
        data: {
            isbn: isbn
        },
        type: "get",
        async: false
    }).done(updateSelectedInstance)
}

function updateSelectedInstance(instance)
{
    selectedInstance = instance

    let div = document.getElementById("selected-book-instance")

    if (instance == null) {
        div.innerHTML = ""
        return
    }

    let availabilityPart = `
        <tr>
            <td>Status</td>
            <td>${instance.status.match(/([A-Z]?[^A-Z]*)/g).slice(0,-1).join(" ")}</td>
        </tr>
    `

    if (instance.status === "Available") {
        availabilityPart += `
            <tr>
                <td>Borrow</td>
                <td>
                    <table>
                        <tr>
                            <td><input id="borrower-username-input"></td>
                            <td>
                                <select id="borrower-borrow-type">
                                    <option value="1">Borrow home</option>                    
                                    <option value="2">Borrow in library</option>                    
                                </select>
                            </td>
                            <td><button onclick="borrowSelectedBook()">Borrow</button></td>
                        </tr>
                    </table>
                </td>
            </tr>
        `
    } else {
            var returnButton = ""
            if (instance.borrower.username === username || isLibrarian) {
                returnButton = "<button onclick=\"returnSelectedBook()\"> return </button>"
            }

            availabilityPart += `
            <tr>
                <td>Borrower name</td>
                <td>${instance.borrower.firstname + " " + instance.borrower.lastname} ${returnButton} </td>
            </tr>
        `
    }

    let html = `
    <table>
        <tbody>
            <tr>
                <td>ID</td>
                <td>${instance.book.id}</td>
            </tr>
            <tr>
                <td>ISBN</td>
                <td>${instance.isbn}</td>
            </tr>
            <tr>
                <td>Title</td>
                <td>${instance.book.title}</td>
            </tr>
            <tr>
                <td>Description</td>
                <td>${instance.book.description}</td>
            </tr>
            ${availabilityPart}
        </tbody>
    </table>
    <hr>`

    div.innerHTML = html
}

function returnSelectedBook() {
    $.ajax({
        url: "/api/returnBook",
        data: {
            isbn: selectedInstance.isbn
        },
        type: "post",
        async: false
    }).done(() => {
        selectBookInstance(selectedInstance.isbn)
        search()
    })
}

function borrowSelectedBook() {
    $.ajax({
        url: "/api/borrowBook",
        data: {
            isbn: selectedInstance.isbn,
            username: getDesiredBorrowerUsername(),
            type: getBorrowType()
        },
        type: "post",
        async: false
    }).done(() => {
        selectBookInstance(selectedInstance.isbn)
        search()
    })
}

function isNumeric(str) {
    if (typeof str != "string") return false
    return !isNaN(str) && !isNaN(parseFloat(str))
}