var Income = 0.;
var Required = 0.;
var ToSave = 0.;
var Other = 0.;

function updateOne(name, sum) {
    var percentage = sum / Income;
    $(`.${name}Sum`).html(toCurrency(sum, "RUB", 'Ru-ru'));
    $(`.${name}SumPercentage`).html(Math.ceil(percentage * 100), 2);
}

function updateSummary() {
    updateOne("expectedIncome", Income);
    updateOne("toSave", ToSave);
    updateOne("requiredPayment", Required);
    updateOne("otherPayment", Other);
    let left =  Income - ToSave - Required - Other;
    updateOne("otherPaymentNotPlanned", left);
    var dayAmount = new Date($("#year").val(), $("#month").val() , 0).getDate()
    $("#everyDayMoney").html(toCurrency( left / dayAmount  ,"RUB", 'Ru-ru'));
}

const toCurrency = (n, curr, LanguageFormat = undefined) =>

    Intl.NumberFormat(LanguageFormat, {style: 'currency', currency: curr}).format(n);

function generateInputs(name, nameDesc, value) {
    return "<div class=\"row mt-1\">\n" +
        "          <div class=\"col\">\n" +
        `            <input class=\"form-control ${name}Name\" value='${nameDesc}'>\n`+
        "          </div>\n" +
        "          <div class=\"col\">\n" +
        `            <input class=\"form-control ${name}Amount\" value='${value}'>\n` +
        "          </div>\n" +
        "        </div>"
}

function scriptCard(name) {

    $(`#${name}Add`).click(function () {
        $(`#${name}`).append(generateInputs(name," ", 0))
    })


    $(document).on('change', `.${name}Amount`, function(){
        var sum = 0;
        $(`.${name}Amount`).each(function (e) {
            sum += Number($( this ).val());
        })
        switch (name) {
            case "expectedIncome": Income = sum;
                break;
            case "toSave": ToSave = sum;
                break;
            case "requiredPayment": Required = sum;
                break;
            case "otherPayment": Other = sum;
        }
        updateSummary();
    })

}
scriptCard("expectedIncome")
scriptCard("toSave")
scriptCard("requiredPayment")
scriptCard("otherPayment")


function median(values){
    if(values.length ===0) throw new Error("No inputs");

    values.sort(function(a,b){
        return a-b;
    });

    var half = Math.floor(values.length / 2);

    if (values.length % 2)
        return values[half];

    return (values[half - 1] + values[half]) / 2.0;
}

function previousDataProcces(array) {
    // медиана или среднее
    return toCurrency(median(array), "RUB", 'Ru-ru')
}

function parseReports(data) {
    let analysis = data.map(x => x.analysis);
    let incomeMean = previousDataProcces(analysis.map(x => x.income));
    $("#previousIncome").html(incomeMean);
    let requiredMean = previousDataProcces(analysis.map(x => x.requirePayments));
    $("#requiredPaymentPrevious").html(requiredMean);
    let toSaveMean = previousDataProcces(analysis.map(x => x.saved));
    $("#savedPreviously").html(requiredMean);

    let otherMean = median(analysis.map(x => x.other));


}

$.ajax({
    url: "/report/all",
    method: 'GET',
    contentType: "application/json",
    success: function (response) {
        parseReports(response)
        console.log("ВСЕ ОКЕЙ")
    },
    error: function (response) {
        console.log("Чето пошло не так...")
    }
})


$.ajax({
    url: "/goals/monthly-fee",
    method: 'GET',
    contentType: "application/json",
    success: function (response) {
        $("#needToSave").html(toCurrency(response.monthlyFee, "RUB", 'Ru-ru'))
    },
    error: function (response) {
        console.log("Чето пошло не так...")
    }
})

function inserInputsFromPlan(name, data) {
    let div = $(`#${name}`);
    div.html(" ")
    for (let i = 0; i < data.length; i++)
    {
        let elem = data[0];
        div.append(generateInputs(name, elem.description, elem.amount));
        switch (name) {
            case "expectedIncome": Income += elem.amount;
                break;
            case "toSave": ToSave += elem.amount;
                break;
            case "requiredPayment": Required += elem.amount;
                break;
            case "otherPayment": Other += elem.amount;
        }
    }
}

function insertPlan(plan) {
    inserInputsFromPlan("expectedIncome", plan.expectedIncome)
    inserInputsFromPlan("toSave", plan.toSave)
    inserInputsFromPlan("requiredPayment", plan.toRequiredPayment)
    inserInputsFromPlan("otherPayment", plan.otherPlans)
    $("#month").val(plan.month).prop('disabled', true)
    $("#year").val(plan.year).prop('disabled', true);
    updateSummary()
}

export default insertPlan;

