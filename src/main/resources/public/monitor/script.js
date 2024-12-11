document.addEventListener("DOMContentLoaded", () => {
    async function fetchData() {
        try {
            const response = await fetch("data.json", {
                method: "GET"
            });
            let data = await response.text();
            return JSON.parse("[" + data.substring(0, data.length - 2) + "]");
        } catch (error) {
            console.error("Error fetching events:", error);
            return [];
        }
    }

    fetchData().then((data) => {

        function generateLabels(numBars) {
            const labels = [];
            for (let i = 1; i <= numBars; i++) {
                labels.push(`Bar ${i}`);
            }
            return labels;
        }
        function renderBarChart(canvasId) {
            const ctx = document.getElementById(canvasId).getContext('2d');
            let labels = []
            let dataSets = []
            const colours = ["rgba(123, 45, 67, 0.6)", "rgba(200, 123, 56, 0.6)", "rgba(34, 156, 78, 0.6)", "rgba(255, 87, 51, 0.6)", "rgba(78, 90, 200, 0.6)", "rgba(34, 100, 255, 0.6)", "rgba(230, 230, 50, 0.6)", "rgba(156, 78, 34, 0.6)", "rgba(78, 34, 123, 0.6)", "rgba(123, 255, 123, 0.6)"]
            const l = generateLabels(data.length);
            for (const d of data) {
                if (!labels.includes(d.path)) labels.push(d.path);
            }
            for (const d of labels) {
                let dataObject = {}
                dataObject.label = d
                dataObject.data = data.filter(data => data.path === d).map(data => data.servingTime);
                dataObject.backgroundColor = colours[labels.indexOf(d)];
                dataObject.borderColor = 'rgba(0, 0, 0, 1)';
                dataObject.borderWidth = 1;
                dataSets.push(dataObject);
            }
            // Create the bar chart
            new Chart(ctx, {
                type: 'bar', data: {
                    labels: l, datasets: dataSets
                }, options: {
                    responsive: true, maintainAspectRatio: false, scales: {
                        x: {
                            display: false // Optionally hide X-axis labels for better readability
                        }, y: {
                            beginAtZero: true
                        }
                    }, plugins: {
                        legend: {
                            display: true, // Show legend
                            position: 'top', // Position the legend (top, bottom, left, right)
                            labels: {
                                boxWidth: 20, // Width of the color box
                                padding: 10   // Padding between legend items
                            }
                        }, tooltip: {
                            enabled: true
                        }
                    }
                }
            });
        }

        renderBarChart('overview');
    })

});