import { useState } from "react";
import reactLogo from "./assets/react.svg";
import viteLogo from "/vite.svg";

function App() {
    const [count, setCount] = useState(0);

    return (
        <div className="flex flex-col items-center justify-center min-h-screen bg-red-200 p-6">
            <div className="flex gap-6">
                <a href="https://vite.dev" target="_blank">
                    <img src={viteLogo} className="w-20" alt="Vite logo" />
                </a>
                <a href="https://react.dev" target="_blank">
                    <img src={reactLogo} className="w-20" alt="React logo" />
                </a>
            </div>
            <h1 className="text-3xl font-bold mt-4 text-blue-600">Vite + React + Tailwind</h1>
            <div className="card bg-white shadow-lg p-6 rounded-lg mt-4">
                <button
                    className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-700 transition"
                    onClick={() => setCount((count) => count + 1)}
                >
                    Count is {count}
                </button>
                <p className="mt-4 text-gray-700">
                    Edit <code className="bg-gray-200 px-2 py-1 rounded">src/App.tsx</code> and save to test HMR.
                </p>
            </div>
            <p className="mt-6 text-gray-500">Click on the logos to learn more</p>
        </div>
    );
}

export default App;
