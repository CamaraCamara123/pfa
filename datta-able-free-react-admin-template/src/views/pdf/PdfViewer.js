/* eslint-disable react/prop-types */
import React from 'react';
import { Viewer } from '@react-pdf-viewer/core';
// import { GlobalWorkerOptions } from 'pdfjs-dist/build/pdf';


import '@react-pdf-viewer/core/lib/styles/index.css';

// Set the workerSrc property
// GlobalWorkerOptions.workerSrc = `https://cdnjs.cloudflare.com/ajax/libs/pdf.js/${PdfJs.version}/pdf.worker.js`;

export default function PDFViewer({ pdfUrl }) {
  console.log('url ', pdfUrl);
  return (
    <div
      style={{
        border: '1px solid rgba(0, 0, 0, 0.3)',
        height: '750px'
      }}
    >
      <Viewer fileUrl={pdfUrl} />
    </div>
  );
}
